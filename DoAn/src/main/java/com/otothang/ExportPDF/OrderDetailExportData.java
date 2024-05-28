package com.otothang.ExportPDF;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.otothang.Service.OrderDetailSevice;
import com.otothang.Service.OrderSevice;
import com.otothang.models.Order;
import com.otothang.models.OrderDetail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailExportData {
    private Order order;
    @Autowired
    private OrderDetailSevice orderDetailService;

    public OrderDetailExportData(Order order, OrderDetailSevice orderDetailService) {
        this.order = order;
        this.orderDetailService = orderDetailService;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException, TemplateException {
        List<OrderDetail> orderDetails = orderDetailService.getByOrderId(order);

        // Tạo một đối tượng Configuration
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(OrderDetailExportData.class, "/templates");

        // Tải mẫu HTML từ file
        Template template = configuration.getTemplate("order_detail_template.ftl");

        // Tạo một Map chứa dữ liệu cần điền vào mẫu HTML
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("order", order);
        dataModel.put("orderDetails", orderDetails);

        // Tạo một StringWriter để chứa HTML kết quả
        StringWriter stringWriter = new StringWriter();

        // Điền dữ liệu vào mẫu HTML và ghi vào StringWriter
        template.process(dataModel, stringWriter);

        // Chuyển đổi HTML đã tạo sang PDF bằng cách sử dụng ITextRenderer
        String htmlContent = stringWriter.toString();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        ITextRenderer renderer = new ITextRenderer();



        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());

        document.close();
    }
}
