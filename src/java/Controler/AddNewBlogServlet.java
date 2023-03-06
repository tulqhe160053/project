/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controler;

import Dao.BlogDAO;
import Dao.CategoryDAO;
import Model.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class AddNewBlogServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        BlogDAO bd = new BlogDAO();
        CategoryDAO cat = new CategoryDAO();
        List<Category> category = cat.selectAll();
        request.setAttribute("category", category);
        request.getRequestDispatcher("blog/addNewBlog.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        BlogDAO bd = new BlogDAO();
        CategoryDAO cat = new CategoryDAO();
        List<Category> category = cat.selectAll();
        request.setAttribute("category", category);
        
        request.getRequestDispatcher("blog/addNewBlog.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Model.Users user = (Model.Users) session.getAttribute("user");
        int userId = user.getUserID();
        
        String title = request.getParameter("title");
        String des = request.getParameter("des");
        String content = request.getParameter("content");
        String catid = request.getParameter("catid");
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        BlogDAO bd = new BlogDAO();
        Part filePart = request.getPart("image");
        String imageFileName = filePart.getSubmittedFileName();
        InputStream is = filePart.getInputStream();
        byte[] data = new byte[is.available()];
        is.read(data);
        bd.InsertBlog(userId, catid, title, des, content, date, imageFileName);
        response.sendRedirect("mylistblog");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
