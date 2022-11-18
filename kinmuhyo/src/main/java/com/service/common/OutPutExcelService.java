package com.service.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.constant.PropertieConst;
import com.form.MakeKinmuForm;

/**
 * 勤務表作成画面のサービスクラス.
 *
 */
@Service
@PropertySource("classpath:/application.properties")
public class OutPutExcelService {

  @Autowired
  private PropertieUtil propertieUtil;

  /**
   * 勤務表エクセル出力処理.
   * param : 画面フォームデータ
   * return : 無し
   */
  public void createKinmuhyo(MakeKinmuForm formData)
      throws IOException, EncryptedDocumentException, InvalidFormatException {
    String filePath = "C:\\pleiades\\workspace\\kinmuhyo\\work\\template.xls";
    Workbook workbook = null;
    FileInputStream in = null;
    OutputStream os = null;
    // 勤務表行数
    final int maxRowCount = Integer.parseInt(propertieUtil.getProperty(PropertieConst.ROW_COUNT));
    // 勤務表月欄
    final String[] monthRowCell = propertieUtil.getProperty(PropertieConst.MONTH_ROW_CELL).split(",");
    // 勤務表開始行
    final int startRow = Integer.parseInt(propertieUtil.getProperty(PropertieConst.START_ROW));
    // 出勤時間行
    final int clockInCell = Integer
        .parseInt(propertieUtil.getProperty(PropertieConst.CLOCK_IN_CELL));
    // 帰社時間セル
    final int clockOutCell = Integer
        .parseInt(propertieUtil.getProperty(PropertieConst.CLOCK_OUT_CELL));
    // 備考セル
    final int remarksOutCell = Integer
        .parseInt(propertieUtil.getProperty(PropertieConst.REMARKS_CELL));
    // 通勤体制セル
    final int commuteOutCell = Integer
        .parseInt(propertieUtil.getProperty(PropertieConst.COMMUTE_CELL));
    try {
      in = new FileInputStream(filePath);
      workbook = WorkbookFactory.create(in);
      // １シート目が対象
      Sheet sheet = workbook.getSheetAt(0);
      DataFormat format = workbook.createDataFormat();
      CellStyle style = workbook.createCellStyle();
      // 月を設定
      sheet.getRow(Integer.parseInt(monthRowCell[0])).getCell(Integer.parseInt(monthRowCell[1])).setCellValue(formData.getTargetMonth());
      // 現在行
      int currentRow = startRow;

      for (int i = 0; i < maxRowCount; i++) {
        Cell cell;
        // 出勤時間
        cell = sheet.getRow(currentRow).getCell(clockInCell);
        cell.setCellValue(formData.getClockIn()[i]);
        style.setDataFormat(cell.getCellStyle().getDataFormat());
        cell.setCellStyle(style);
        cell.setCellValue(formData.getClockIn()[i]);
        cell.setAsActiveCell();
        cell.setCellValue(formData.getClockIn()[i]);
        // 帰社時間
        cell = sheet.getRow(currentRow).getCell(clockOutCell);
        cell.setCellValue(formData.getClockOut()[i]);
        cell.setAsActiveCell();
        // 備考
        sheet.getRow(currentRow).getCell(remarksOutCell).setCellValue(formData.getRemarks()[i]);
        // 通勤体制
        sheet.getRow(currentRow).getCell(commuteOutCell).setCellValue(formData.getCommute()[i]);
        
        currentRow++;
      }
      workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
      sheet.setForceFormulaRecalculation(true);
      os = new FileOutputStream(filePath);
      workbook.write(os);

    } finally {
      if (in != null) {
        in.close();
      }
      if (os != null) {
        os.close();
      }
      if (workbook != null) {
        workbook.close();
      }
    }
  }

}