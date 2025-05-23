package com.example.backend.controller;

@WebServlet("/sign")
public class SignController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("orderData");
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");

        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signedBytes = signature.sign();
            String signatureBase64 = Base64.getEncoder().encodeToString(signedBytes);
            request.setAttribute("signature", "Signature: " + signatureBase64);
        } catch (Exception e) {
            request.setAttribute("signature", "Signing failed: " + e.getMessage());
        }

        request.getRequestDispatcher("/sign.jsp").forward(request, response);
    }
}
