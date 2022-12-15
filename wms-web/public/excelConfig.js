import FileSaver from 'file-saver';
import * as XLSX from 'xlsx'

export function exportExcel(tableName) {
    const xlsxParam = {raw: true};//转换成excel时，使用原始的格式
    const wb = XLSX.utils.table_to_book(document.querySelector(tableName), xlsxParam);//outTable为列表id
    const wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
    });
    try {
        FileSaver.saveAs(
            new Blob([wbout], { type: "application/octet-stream;charset=utf-8" }),
            "HelloWorld.xlsx"
        );
    } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
    }
    return wbout;}
