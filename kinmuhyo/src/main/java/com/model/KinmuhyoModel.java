package com.model;

import lombok.Data;

/**
 * 勤務表のモデルクラス.
 * 勤務表に表示させるための値を保持する
 */
@Data
public class KinmuhyoModel implements Cloneable {

  /**
   * 日.
   */
  private String day;

  /**
   * 曜日.
   */
  private String dayOfWeek;

  /**
   * 出勤時間.
   */
  private String clockIn;

  /**
   * 帰社時間.
   */
  private String clockOut;

  /**
   * 備考.
   */
  private String remarks;

  /**
   * 通勤体制.
   */
  private String commute;
  
  /**
   * 休日フラグ.
   */
  private Boolean holidayFlg = false;

  @Override
  public KinmuhyoModel clone() {

    KinmuhyoModel model = new KinmuhyoModel();
    try {
      model = (KinmuhyoModel) super.clone();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return model;
  }

}