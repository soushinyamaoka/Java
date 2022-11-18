package com.form;

import lombok.Data;

/**
 * 勤務表のフォームクラス.
 * 勤務表作成画面からPOSTさせるための値を保持する
 */
@Data
public class MakeKinmuForm {

  /**
   * 日.
   */
  private String[] day;

  /**
   * 曜日.
   */
  private String[] dayOfWeek;

  /**
   * 出勤時間.
   */
  private String[] clockIn;

  /**
   * 帰社時間.
   */
  private String[] clockOut;

  /**
   * 備考.
   */
  private String[] remarks;

  /**
   * 通勤体制.
   */
  private String[] commute;
  
  /**
   * 休日フラグ.
   */
  private Boolean[] holidayFlg;
  
  /**
   * 対象月.
   */
  private String targetMonth;

}