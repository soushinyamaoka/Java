package com.service.common;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 勤務表作成画面のサービスクラス.
 *
 */
@Service
@PropertySource("classpath:/application.properties")
public class PropertieUtil {

  @Autowired
  private Environment env;

  /**
   * プロパティ設定値取得処理.
   * 引数を元にプロパティ設定値を取得する
   * param  : key    キー
   * return : プロパティ設定値
   */
  public String getProperty(String key) {
    
    if (StringUtils.isEmpty(key)) {
      return "";
    }

    return env.getProperty(key);
  }
  

}