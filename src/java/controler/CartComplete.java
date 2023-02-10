/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dao.CartDAO;
import dao.ProductDAO;
import dao.ProductImgDAO;
import dao.ShipAddressDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Cart;
import model.Product;
import model.ProductImg;
import model.ShipAddress;
import model.Users;

/**
 *
 * @author Tu
 */
public class CartComplete extends HttpServlet {

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
        doGet(request, response);
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
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                Users user = (Users) session.getAttribute("user");
                if (user == null) {
                    request.getRequestDispatcher("home").forward(request, response);
                }
                CartDAO cart_dao = new CartDAO();
                ArrayList<Cart> list = cart_dao.selectByUserId(user.getUserID());
                int total = 0;
                for (Cart cart : list) {
                    Product p = cart.getProduct();
                    total += p.getSellPrice() * cart.getAmount();
                }
                request.setAttribute("total", total);
                request.setAttribute("list", list);
                ProductImgDAO productImg_dao = new ProductImgDAO();
                ArrayList<ProductImg> list_productImg = productImg_dao.selectAll();
                
                ShipAddressDAO address_dao = new ShipAddressDAO();
                ArrayList<ShipAddress> list_shipAddress = address_dao.getByUserId(user.getUserID());
                request.setAttribute("shipaddress", list_shipAddress.get(0));
                request.setAttribute("list_productImg", list_productImg);
                request.getRequestDispatcher("/cart/ViewCartComplete.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("login").forward(request, response);
            }
        } catch (Exception e) {
        }
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
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                Users user = (Users) session.getAttribute("user");
                request.getRequestDispatcher("/cart/ViewCartComplete.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("login").forward(request, response);
            }
        } catch (Exception e) {
        }
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