package com.service.common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.constant.DateTimeConst;

/**
 * 日付操作用ユーティリティクラス.
 *
 */
@Service
public class DateTimeUtil {

  /**
   * 曜日取得処理.
   * 引数を元に曜日を取得する
   * param  : ld    日付
   * return : 曜日
   */
  public static String getDayOfWeek(LocalDate ld) {

    String result = "";

    //曜日を取得
    DayOfWeek dayOfWeek = ld.getDayOfWeek();

    switch (dayOfWeek) {
    case SUNDAY: // 日曜日
      result = DateTimeConst.SUNDAY;
      break;
    case MONDAY: // 月曜日
      result = DateTimeConst.MONDAY;
      break;
    case TUESDAY: // 火曜日
      result = DateTimeConst.TUESDAY;
      break;
    case WEDNESDAY: // 水曜日
      result = DateTimeConst.WEDNESDAY;
      break;
    case THURSDAY: // 木曜日
      result = DateTimeConst.THURSDAY;
      break;
    case FRIDAY: // 金曜日
      result = DateTimeConst.FRIDAY;
      break;
    case SATURDAY: // 土曜日
      result = DateTimeConst.SATURDAY;
      break;
    default:
      result = "";
      break;

    }

    return result;
  }

}