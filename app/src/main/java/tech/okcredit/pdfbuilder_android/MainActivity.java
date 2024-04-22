package tech.okcredit.pdfbuilder_android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PDFPrint;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.ai.billinguicomponents.R;

import java.io.File;

import tech.okcredit.create_pdf.activity.PDFViewerActivity;
import tech.okcredit.create_pdf.utils.FileManager;
import tech.okcredit.create_pdf.utils.PDFUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        LinearLayout layoutPdfCreator = (LinearLayout) findViewById(R.id.layoutGeneratePdf);
        LinearLayout layoutHtmlPdfCreator = (LinearLayout) findViewById(R.id.layoutGenerateHtmlPdf);

        layoutPdfCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PreviewActivity.class));
            }
        });

        layoutHtmlPdfCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileManager.Companion.getInstance().cleanTempFolder(getApplicationContext());
                // Create Temp File to save Pdf To
                final File savedPDFFile = FileManager.Companion.getInstance().createTempFile(getApplicationContext(), "pdf", false);
                // Generate Pdf From Html
                PDFUtil.generatePDFFromHTML(getApplicationContext(), savedPDFFile, "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<body>\n" +
                        "<div style=\"box-sizing: border-box; padding: 0 5px; font-size: 12px; line-height: 2.2; break-before: page;\" class=\"print\">\n" +
                        "    <div style=\"margin-top: 20px;\">\n" +
                        "        <table style=\"width: 100%;\">\n" +
                        "            <thead>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"14\" style=\"border: none; break-before: page;\">\n" +
                        "                        <div style=\"margin: 0px; padding: 0px; font-size: 12px;\">\n" +
                        "                            <div style=\"text-align: center; font-weight: bold\">\n" +
                        "                                TAX INVOICE\n" +
                        "                            </div>\n" +
                        "                            <div style=\"display: flex; justify-content: space-between; align-items: center; line-height: 1.3;\">\n" +
                        "                                <div>\n" +
                        "                                    <div style=\"margin-top: 5px;\">Bill By</div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 200px; display: inline-block; font-weight: bold;\">testing business</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 200px; display: inline-block;\">3, Rock Town Colony Rd, Rock Town Residents Colony, Sai Nagar, Chanakyapuri, Hyderabad, Telangana 500068, India</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 100px; display: inline-block;\">Contact Number</span>\n" +
                        "                                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                                        <span>9542974592</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 100px; display: inline-block;\">GSTIN</span>\n" +
                        "                                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                                        <span></span>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                                <div style=\"max-width: 33%\">\n" +
                        "                                    <div>Bill To</div>\n" +
                        "                                    <div style=\"font-weight: bold;\">\n" +
                        "                                        <span style=\"width: 80px; display: inline-block;\">Cust2</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 200px; display: inline-block;\">cust2Addr</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 100px; display: inline-block;\">Contact Number</span>\n" +
                        "                                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                                        <span>9999999999</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <span style=\"width: 100px; display: inline-block;\">GSTIN</span>\n" +
                        "                                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                                        <span></span>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"14\">\n" +
                        "                        <div style=\"width: 100%; border: solid 0.5px #bdbdbd\"></div>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr style=\"font-size: 12px;line-height: 1.6\">\n" +
                        "                    <td colspan=\"6\">\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Invoice Number</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <b>INV0002</b>\n" +
                        "                    </td>\n" +
                        "                    <td colspan=\"5\">\n" +
                        "                        <span>IRN: </span>\n" +
                        "                    </td>\n" +
                        "                    <td colspan=\"3\">\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Invoice Date</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <b>17-04-2024</b>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"14\">\n" +
                        "                        <div style=\"width: 100%; border: solid 0.5px #bdbdbd;\"></div>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr style=\"font-size: 11px; line-height: 2\">\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: center;\">SN.</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: left;\">Item Name</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: center;\">HSN Code</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">MRP(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">Rate(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">Discount(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">GST(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">Net Rate(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">Quantity</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">Taxable(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">CGST(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">SGST(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">GST(₹)</td>\n" +
                        "                    <td style=\"color: black; font-weight: bold; text-align: right;\">Total(₹)</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"14\">\n" +
                        "                        <div style=\"width: 100%; border: solid 0.5px #bdbdbd\"></div>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </thead>\n" +
                        "            <tbody>\n" +
                        "                <tr style=\"font-size: 12px;\">\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: left; line-height: 2;\">PURIFYING NEEM FACIAL WIPES 25'S</td>\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">33079090</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">120.00</td>\n" +
                        "                    <td style=\"text-align:right;line-height:2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                </tr>\n" +
                        "                \n" +
                        "                                <tr style=\"font-size: 12px;\">\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: left; line-height: 2;\">PURIFYING NEEM FACIAL WIPES 25'S</td>\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">33079090</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">120.00</td>\n" +
                        "                    <td style=\"text-align:right;line-height:2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                </tr>\n" +
                        "                                <tr style=\"font-size: 12px;\">\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: left; line-height: 2;\">PURIFYING NEEM FACIAL WIPES 25'S</td>\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">33079090</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">120.00</td>\n" +
                        "                    <td style=\"text-align:right;line-height:2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                </tr>\n" +
                        "                                <tr style=\"font-size: 12px;\">\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: left; line-height: 2;\">PURIFYING NEEM FACIAL WIPES 25'S</td>\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">33079090</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">120.00</td>\n" +
                        "                    <td style=\"text-align:right;line-height:2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                </tr>\n" +
                        "                                <tr style=\"font-size: 12px;\">\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: left; line-height: 2;\">PURIFYING NEEM FACIAL WIPES 25'S</td>\n" +
                        "                    <td style=\"text-align: center; line-height: 2;\">33079090</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">120.00</td>\n" +
                        "                    <td style=\"text-align:right;line-height:2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">1</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">0.00</td>\n" +
                        "                    <td style=\"text-align: right; line-height: 2;\">8474.00</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"14\" style=\"border-bottom: 1px solid #bdbdbd;\"></td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"9\" style=\"color: black; font-size: 12px; font-weight: bold; text-align: left; padding: 5px 0;\">Total</td>\n" +
                        "                    <td style=\"color: black; font-size: 12px; font-weight: bold; text-align: right; padding: 5px 0;\">8474.00</td>\n" +
                        "                    <td style=\"color: black; font-size: 12px; font-weight: bold; text-align:right;padding:5px0;\">0.00</td>\n" +
                        "                    <td style=\"color: black; font-size: 12px; font-weight: bold; text-align:right;padding:5px0;\">0.00</td>\n" +
                        "                    <td style=\"color: black; font-size: 12px; font-weight: bold; text-align:right;padding:5px0;\">0.00</td>\n" +
                        "                    <td style=\"color: black; font-size: 12px; font-weight: bold; text-align:right;padding:5px0;\">8474.00</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td colspan=\"14\" style=\"border-bottom: 1px solid #bdbdbd;\"></td>\n" +
                        "                </tr>\n" +
                        "            </tbody>\n" +
                        "        </table>\n" +
                        "    </div>\n" +
                        "    <div style=\"margin: 0px; padding: 0px; break-inside: avoid;line-height: 1.4\">\n" +
                        "        <div style=\"display: flex; justify-content: space-between; margin-top: 20px;\">\n" +
                        "            <div style=\"display: flex; align-items: center;\">\n" +
                        "                <div>\n" +
                        "                    <img src=\"https://storage.googleapis.com/prod__dynamicui__public__dataset/billing_placeholder_qr.png\" style=\"height: 175px; width: 175px;\" />\n" +
                        "                </div>\n" +
                        "                <div style=\"margin-left: 20px\">\n" +
                        "                    <div style=\"font-size: 12px; font-weight: bold; margin-bottom: 20px;\">Scan & Pay via any UPI App</div>\n" +
                        "                    <div>\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Invoice Number</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <span>INV0002</span>\n" +
                        "                    </div>\n" +
                        "                    <div>\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Total Invoice</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <span>8474.00</span>\n" +
                        "                    </div>\n" +
                        "                    <div>\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Bill Discount</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <span>(0.00)</span>\n" +
                        "                    </div>\n" +
                        "                    <div>\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Round Off</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <span>(0.00)</span>\n" +
                        "                    </div>\n" +
                        "                    <div>\n" +
                        "                        <span style=\"width: 100px; display: inline-block;\">Payable Amount</span>\n" +
                        "                        <span style=\"margin-right: 16px;\">:</span>\n" +
                        "                        <span>8474.00</span>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <div style=\"display: flex; justify-content: center; align-items: flex-end; margin-bottom: 20px; padding-bottom: 20px;\">\n" +
                        "                CUSTOMER SIG. WITH STAMP\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>", new PDFPrint.OnPDFPrintListener() {
                    @Override
                    public void onSuccess(File file) {
                        // Open Pdf Viewer
                        Uri pdfUri = Uri.fromFile(savedPDFFile);

                        Intent intentPdfViewer = new Intent(MainActivity.this, PDFViewerActivity.class);
                        intentPdfViewer.putExtra(PDFViewerActivity.PDF_FILE_URI, pdfUri);

                        startActivity(intentPdfViewer);
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });

    }
}
