package com.model;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 勤務表作成画面のモデルクラス.
 * 画面に表示させるための値を保持する
 */
@Data
@Component
public class MakeKinmuModel implements Cloneable {

  /**
   * 勤務表.
   */
  private ArrayList<KinmuhyoModel> kinmuhyoList;
 
  /**
   * 対象月.
   */
  private String targetMonth;

  @Override
  public MakeKinmuModel clone() {

    MakeKinmuModel model = new MakeKinmuModel();
    try {
      model = (MakeKinmuModel) super.clone();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return model;
  }
}