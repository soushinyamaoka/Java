package com.controller.makeKinmu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.form.MakeKinmuForm;
import com.model.MakeKinmuModel;
import com.service.MakeKinmuService;

/**
 * 勤務表作成画面のコントロールクラス.
 *
 */
@Controller
public class MakeKinmuController {

  @Autowired
  private MakeKinmuService makeKinmuService;

  /**
   * 勤務表作成画面初期表示処理.
   * param : 無し
   * return : 無し
   */
  @GetMapping("/makeKinmu")
  public String init(Model model) {

    MakeKinmuModel makeKinmuModel = makeKinmuService.initKinmuHyou();

    model.addAttribute("makeKinmuModel", makeKinmuModel);
//    model.addAttribute("kinmuhyoList", makeKinmuModel.getKinmuhyoList());
    return "makeKinmu/makeKinmu";
  }
  
  /**
   * 勤務表出力処理.
   * param : 無し
   * return : 無し
   */
  @PostMapping("/makeKinmu/output")
  public String output(@ModelAttribute("makeKinmuModel") MakeKinmuForm formData, Model model) {

    makeKinmuService.outputKinmuHyou(formData);

    MakeKinmuModel makeKinmuModel = makeKinmuService.initKinmuHyou();
    model.addAttribute("makeKinmuModel", makeKinmuModel);
    return "makeKinmu/makeKinmu";
  }
}