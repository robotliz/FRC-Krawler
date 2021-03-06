package com.team2052.frckrawler.fragments.scout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.firebase.crash.FirebaseCrash;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.team2052.frckrawler.R;
import com.team2052.frckrawler.database.metric.MetricValue;
import com.team2052.frckrawler.db.Event;
import com.team2052.frckrawler.db.MatchComment;
import com.team2052.frckrawler.db.MatchDatum;
import com.team2052.frckrawler.db.Metric;
import com.team2052.frckrawler.db.MetricDao;
import com.team2052.frckrawler.db.Robot;
import com.team2052.frckrawler.tba.JSON;
import com.team2052.frckrawler.util.MetricHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ScoutMatchFragment extends BaseScoutFragment {
    private static final String TAG = "ScoutMatchFragment";
    private static String MATCH_TYPE = "MATCH_TYPE";
    @BindView(R.id.match_number_input)
    TextInputLayout mMatchNumberInput;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int mMatchType;
    Observable<List<MetricValue>> metricValueObservable = Observable
            .combineLatest(matchNumberObservable(), robotObservable(), MetricValueUpdateParams::new)
            .map(valueParams -> {
                List<MetricValue> metricValues = Lists.newArrayList();
                final QueryBuilder<Metric> metricQueryBuilder = rxDbManager.getMetricsTable().query(MetricHelper.MATCH_PERF_METRICS, null, mEvent.getGame_id(), true)
                        .orderDesc(MetricDao.Properties.Priority)
                        .orderAsc(MetricDao.Properties.Id);
                List<Metric> metrics = metricQueryBuilder.list();
                for (int i = 0; i < metrics.size(); i++) {
                    Metric metric = metrics.get(i);
                    //Query for existing data
                    QueryBuilder<MatchDatum> matchDataQueryBuilder = rxDbManager
                            .getMatchDataTable()
                            .query(valueParams.robot.getId(), metric.getId(), Long.valueOf(valueParams.match_num), mMatchType, mEvent.getId());
                    MatchDatum currentData = matchDataQueryBuilder.unique();
                    //Add the metric values
                    metricValues.add(new MetricValue(metric, currentData == null ? null : JSON.getAsJsonObject(currentData.getData())));
                }
                return metricValues;
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    Observable<String> metricCommentObservable = Observable
            .combineLatest(matchNumberObservable(), robotObservable(), MetricValueUpdateParams::new)
            .map(valueParams -> {
                final QueryBuilder<MatchComment> matchCommentQueryBuilder
                        = rxDbManager.getMatchCommentsTable().query(Long.valueOf(valueParams.match_num), mMatchType, valueParams.robot.getId(), mEvent.getId(), null);
                MatchComment mMatchComment = matchCommentQueryBuilder.unique();
                String comment = null;
                if (mMatchComment != null)
                    comment = mMatchComment.getComment();
                return Strings.nullToEmpty(comment);
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    public static ScoutMatchFragment newInstance(Event event, int type) {
        ScoutMatchFragment scoutMatchFragment = new ScoutMatchFragment();
        Bundle args = new Bundle();
        args.putInt(MATCH_TYPE, type);
        args.putLong(EVENT_ID, event.getId());
        scoutMatchFragment.setArguments(args);
        return scoutMatchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMatchType = getArguments().getInt(MATCH_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scouting_match, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.match_scout);

        mMatchNumberInput.getEditText().setText("1");
        mMatchNumberInput.getEditText().setSelection(mMatchNumberInput.getEditText().getText().length());

        subscriptions.add(RxTextView.afterTextChangeEvents(mMatchNumberInput.getEditText())
                .filter(event -> {
                    try {
                        Integer.parseInt(event.editable().toString());
                        mMatchNumberInput.setErrorEnabled(false);
                        mMatchNumberInput.setError("");
                    } catch (NumberFormatException e1) {
                        mMatchNumberInput.setErrorEnabled(true);
                        mMatchNumberInput.setError("Invalid Number");
                        return false;
                    }
                    return true;
                }).debounce(500, TimeUnit.MILLISECONDS).subscribe(onNext -> updateMetricValues()));
    }

    private Observable<Integer> matchNumberObservable() {
        return Observable.defer(() -> Observable.just(Integer.parseInt(mMatchNumberInput.getEditText().getText().toString())));
    }

    @Override
    public void updateMetricValues() {
        subscriptions.add(metricValueObservable.subscribe(this::setMetricValues, onError -> {
            //Most likely part of the robot observable not being initiated, no big deal
            if (onError instanceof ArrayIndexOutOfBoundsException || onError instanceof NumberFormatException) {
                return;
            }
            FirebaseCrash.log("Match: Error Updating Metric Values");
            FirebaseCrash.report(onError);
        }));
        subscriptions.add(metricCommentObservable.subscribe(RxTextView.text(mCommentsView.getEditText()), onError -> {
            //Most likely part of the robot observable not being initiated, no big deal
            if (onError instanceof ArrayIndexOutOfBoundsException || onError instanceof NumberFormatException) {
                return;
            }
            FirebaseCrash.log("Match: Error Updating Comments");
            FirebaseCrash.report(onError);
        }));
    }

    @Override
    public Observable<Boolean> getSaveMetricObservable() {
        return Observable.combineLatest(matchNumberObservable(), robotObservable(), Observable.defer(() -> Observable.just(getValues())), Observable.just(mCommentsView.getEditText().getText().toString()), MatchScoutSaveMetric::new)
                .map(matchScoutSaveMetric -> {
                    //Insert Metric Data
                    boolean saved = false;
                    for (MetricValue metricValue : matchScoutSaveMetric.metricValues) {
                        MatchDatum matchDatum = new MatchDatum(
                                null,
                                mEvent.getId(),
                                matchScoutSaveMetric.robot.getId(),
                                metricValue.getMetric().getId(),
                                mMatchType,
                                matchScoutSaveMetric.matchNum,
                                new Date(),
                                JSON.getGson().toJson(metricValue.getValue()));
                        if (rxDbManager.getMatchDataTable().insertMatchData(matchDatum) && !saved)
                            saved = true;
                    }


                    if (!Strings.isNullOrEmpty(matchScoutSaveMetric.comment)) {
                        MatchComment matchComment = new MatchComment(null);
                        matchComment.setMatch_number((long) matchScoutSaveMetric.matchNum);
                        matchComment.setMatch_type(mMatchType);
                        matchComment.setRobot(matchScoutSaveMetric.robot);
                        matchComment.setEvent(mEvent);
                        matchComment.setComment(matchScoutSaveMetric.comment);
                        if (rxDbManager.getMatchCommentsTable().insertMatchComment(matchComment) && !saved)
                            saved = true;
                    }
                    return saved;
                });
    }

    @Deprecated
    private int getMatchNumber() {
        try {
            return Integer.parseInt(mMatchNumberInput.getEditText().getText().toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Deprecated
    private boolean isMatchNumberValid() {
        return getMatchNumber() != -1;
    }

    private static class MetricValueUpdateParams {
        Integer match_num;
        Robot robot;

        public MetricValueUpdateParams(Integer match_num, Robot robot) {
            this.match_num = match_num;
            this.robot = robot;
        }
    }

    public class MatchScoutSaveMetric {
        Integer matchNum;
        Robot robot;
        List<MetricValue> metricValues;
        String comment;

        public MatchScoutSaveMetric(Integer matchNum, Robot robot, List<MetricValue> metricValues, String comment) {
            this.matchNum = matchNum;
            this.robot = robot;
            this.metricValues = metricValues;
            this.comment = comment;
        }
    }
}
