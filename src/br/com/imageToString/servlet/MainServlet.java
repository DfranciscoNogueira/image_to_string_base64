package br.com.imageToString.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/teste")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        String urlImage = req.getServletContext().getResource("image/indice.jpg").getPath();

        File file = new File(urlImage);

        PrintWriter writer = resp.getWriter();

        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>");
        sb.append("<html lang='pt-br'>");
        sb.append("<head>");
        sb.append("<title>Estudo</title>");
        sb.append("<meta charset='utf-8'/>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<br>");
        sb.append("<br>");

        String imagem = convertImageToStringBase64(file.getAbsolutePath());

        sb.append("<img id='imagem' src='data:image/jpeg;base64," + imagem + "'>");

        sb.append("</body>");
        sb.append("</html>");

        writer.print(sb.toString());

    }

    private String convertImageToStringBase64(String urlImage) throws IOException {
        Path path = Paths.get(urlImage);
        byte[] data = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] convertFileToByte(File file) throws IOException {
        String imagemToString = convertImageToStringBase64(file.getAbsolutePath());
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imagemToString);
        return imageBytes;
    }

    private File convertByteToFile(byte imageBytes) throws IOException {
        File file = File.createTempFile("imagem", ".jpg");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imageBytes);
        fos.close();
        return file;
    }

}
