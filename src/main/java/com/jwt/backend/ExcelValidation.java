package com.jwt.backend;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExcelValidation {
    private final List<String> errorMsg = new ArrayList<>();
    private static final int LAST_COLUMN_NUMBER = 2;

    private void CellsValidation(XSSFSheet hoja) {
        int rowStart = Math.min(3, hoja.getFirstRowNum()) + 1;
        int rowEnd = Math.max(3, hoja.getLastRowNum());

        for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
            Row r = hoja.getRow(rowNum);

            if (r == null) {
                continue;
            }

            int lastColumn = Math.max(r.getLastCellNum(), LAST_COLUMN_NUMBER);

            for (int cn = 0; cn < lastColumn; cn++) {
                Cell c = r.getCell(cn);

                switch (cn) {
                    case 0, 1, 2, 4:
                        if ((c != null) && (c.getCellType() == CellType.NUMERIC)) {
                            System.out.println((int) c.getNumericCellValue());
                        } else {
                            assert c != null;
                            errorMsg.add("Error de tipo en la celda: " + c.getAddress() + ". Se esperaba un dato numerico!");
                        }
                        break;
                    case 3:
                        if ((c != null) && (c.getCellType() == CellType.STRING)) {
                            System.out.println(c.getStringCellValue());
                        } else {
                            assert c != null;
                            errorMsg.add("Error de tipo en la celda: " + c.getAddress() + ". Se esperaba un dato de tipo texto!");
                        }
                        break;
                }
            }
        }
    }

    public ExcelValidation(XSSFSheet hoja) {
        int i;

        // Validacion del formato de las columnas
        Row fila = hoja.getRow(0);
        for (i = 0; i < 5; i++) {
            Cell celda = fila.getCell(i);
            if (!"Id Quipu".equals(celda.getStringCellValue()) && i == 0) {
                this.errorMsg.add(responseError(celda.getAddress()));
            } else if (!"Id Empresa".equals(celda.getStringCellValue()) && i == 1) {
                this.errorMsg.add(responseError(celda.getAddress()));
            } else if (!"SubCuenta".equals(celda.getStringCellValue()) && i == 2) {
                this.errorMsg.add(responseError(celda.getAddress()));
            } else if (!"Descripcion".equals(celda.getStringCellValue()) && i == 3) {
                this.errorMsg.add(responseError(celda.getAddress()));
            } else if (!"Valor".equals(celda.getStringCellValue()) && i == 4) {
                this.errorMsg.add(responseError(celda.getAddress()));
            }
        }

        if (errorMsg.isEmpty()) {
            CellsValidation(hoja);
        }
    }

    private String responseError(CellAddress address) {
        return "El texto de la celda: " + address + " debe ser 'Descripcion'";
    }

}
