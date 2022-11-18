package com.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.constant.DateTimeConst;
import com.constant.PropertieConst;
import com.form.MakeKinmuForm;
import com.model.KinmuhyoModel;
import com.model.MakeKinmuModel;
import com.service.common.DateTimeUtil;
import com.service.common.OutPutExcelService;
import com.service.common.PropertieUtil;

/**
 * 勤務表作成画面のサービスクラス.
 *
 */
@Service
@PropertySource("classpath:/application.properties")
public class MakeKinmuService {

  @Autowired
  private PropertieUtil propertieUtil;

  @Autowired
  private OutPutExcelService outPutExcelService;

  /**
   * 勤務表初期化処理.
   * 勤務表初期表示用の値を生成する
   * param : 無し
   * return : 無し
   */
  public MakeKinmuModel initKinmuHyou() {

    LocalDate now = LocalDate.now();
    String targetDate = String.valueOf(now.getYear())
        + "/" + String.valueOf(now.getMonthValue())
        + "/" + propertieUtil.getProperty(PropertieConst.START_DAY);
    MakeKinmuModel model = createModel(targetDate);

    return model;

  }

  /**
   * 勤務表初期化処理.
   * 勤務表初期表示用の値を生成する
   * param : 無し
   * return : 無し
   */
  public void outputKinmuHyou(MakeKinmuForm formData) {

    try {
      outPutExcelService.createKinmuhyo(formData);
    } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
      // TODO 自動生成された catch ブロック
      System.out.println("エクセル書き込みに失敗しました");
    }

  }

  /**
   * 画面表示用モデル生成処理.
   * 引数の期間分の日付を取得する
   * param : startDate  開始日
   * param : endDate    終了日
   * return : 勤務表作成画面のモデルリスト
   */
  private MakeKinmuModel createModel(String date) {

    // 勤務表行数
    int rowCount = Integer.parseInt(propertieUtil.getProperty(PropertieConst.ROW_COUNT));

    // 勤務表作成画面のモデル
    MakeKinmuModel model = new MakeKinmuModel();
    // 勤務表リストの初期化
    KinmuhyoModel[] array = new KinmuhyoModel[rowCount];
    Arrays.fill(array, new KinmuhyoModel());
    model.setKinmuhyoList(new ArrayList<KinmuhyoModel>(Arrays.asList(array)));

    // 開始日
    LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    // 開始日から終了日までの期間分の日付リスト
    List<LocalDate> dateList = getDatesBetween(startDate);

    // 日にちと曜日を設定
    model = createDaysForDisp(model, dateList);

    // 勤務時間と通勤体制を設定
    model = createTimeDisp(model);

    // 対象月
    model.setTargetMonth(String.valueOf(LocalDate.now().getMonthValue()));

    return model;
  }

  /**
   * 勤務表掲載用日付取得処理.
   * 引数の期間分の日付を取得する
   * param : startDate  開始日
   * param : endDate    終了日
   * return : 日付のリスト
   */
  private List<LocalDate> getDatesBetween(LocalDate startDate) {

    // 勤務表の最終日を取得
    String endDay = propertieUtil.getProperty(PropertieConst.END_DAY);
    LocalDate nextMonth = startDate.plusMonths(1);
    // 終了日を産出
    LocalDate endDate = LocalDate.of(nextMonth.getYear(), nextMonth.getMonthValue(),
        Integer.parseInt(endDay) + 1);
    // 期間分の日付を取得
    List<LocalDate> dateList = startDate.datesUntil(endDate).collect(Collectors.toList());

    return dateList;
  }

  /**
   * 画面表示用日付生成処理.
   * 引数の期間分の日付を取得する
   * param : modelList  勤務表作成画面のモデルリスト
   * param : dateList   日付リスト
   * return : 勤務表作成画面のモデルリスト
   */
  private MakeKinmuModel createDaysForDisp(MakeKinmuModel model, List<LocalDate> dateList) {

    // モデルをコピー
    MakeKinmuModel copyModel = model.clone();
    ArrayList<KinmuhyoModel> copyModelList = copyModel.getKinmuhyoList();
    // 引数の最初の月を取得
    Month currentMonth = dateList.get(0).getMonth();

    // 最初の月の分だけ日にちを取得
    for (int i = 0; i < copyModelList.size(); i++) {

      // 月が変わった場合は終了
      if (currentMonth != dateList.get(i).getMonth()) {
        break;
      }

      // 勤務表モデルをコピー
      KinmuhyoModel m = copyModelList.get(i).clone();
      // 日にちを取得
      m.setDay(Integer.valueOf(dateList.get(i).getDayOfMonth()).toString());
      // 曜日を取得
      m.setDayOfWeek(DateTimeUtil.getDayOfWeek(dateList.get(i)));
      // 休日フラグを設定
      m.setHolidayFlg(DateTimeConst.SATURDAY.equals(m.getDayOfWeek())
          || DateTimeConst.SUNDAY.equals(m.getDayOfWeek()) ? Boolean.TRUE : Boolean.FALSE);
      // 現在月を保持
      currentMonth = dateList.get(i).getMonth();
      // モデルに設定
      copyModelList.set(i, m);

    }

    // 引数の最後の月を取得
    currentMonth = dateList.get(dateList.size() - 1).getMonth();

    int n = dateList.size();
    // 最後の月の分だけ日にちを取得
    for (int i = copyModelList.size() - 1; i > 0; i--) {
      // 日付を取得
      n--;
      LocalDate date = dateList.get(n);

      // 月が変わった場合は終了
      if (currentMonth != date.getMonth()) {
        break;
      }

      // 日にち
      String dayOfMonth = Integer.valueOf(date.getDayOfMonth()).toString();

      // 勤務表モデルをコピー
      KinmuhyoModel m = copyModelList.get(i).clone();
      // 日にちを取得
      m.setDay(dayOfMonth);
      // 曜日を取得
      m.setDayOfWeek(DateTimeUtil.getDayOfWeek(date));
      // 休日フラグを設定
      m.setHolidayFlg(DateTimeConst.SATURDAY.equals(m.getDayOfWeek())
          || DateTimeConst.SUNDAY.equals(m.getDayOfWeek()) ? Boolean.TRUE : Boolean.FALSE);
      // 現在月を保持
      currentMonth = date.getMonth();
      // モデルに設定
      copyModelList.set(i, m);

    }

    copyModel.setKinmuhyoList(copyModelList);

    return copyModel;

  }

  /**
   * 画面表示用勤務時間生成処理.
   * 勤務時間を取得する
   * param : modelList  勤務表作成画面のモデルリスト
   * return : 日付のリスト
   */
  private MakeKinmuModel createTimeDisp(MakeKinmuModel model) {
    // モデルをコピー
    MakeKinmuModel copyModel = model.clone();
    ArrayList<KinmuhyoModel> copyModelList = copyModel.getKinmuhyoList();
    String clockIn = propertieUtil.getProperty(PropertieConst.CLOCK_IN);
    String clockOut = propertieUtil.getProperty(PropertieConst.CLOCK_OUT);
    String commute = propertieUtil.getProperty(PropertieConst.COMMUTE);

    copyModelList.forEach(cModel -> {

      cModel.setClockIn(
          StringUtils.isEmpty(cModel.getDay()) || cModel.getHolidayFlg() ? "" : clockIn); // 出勤時間
      cModel.setClockOut(
          StringUtils.isEmpty(cModel.getDay()) || cModel.getHolidayFlg() ? "" : clockOut); // 帰社時間
      cModel.setCommute(
          StringUtils.isEmpty(cModel.getDay()) || cModel.getHolidayFlg() ? "" : commute); // 通勤体制

    });

    copyModel.setKinmuhyoList(copyModelList);

    return copyModel;
  }

}