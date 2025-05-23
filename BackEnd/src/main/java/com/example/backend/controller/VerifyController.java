package com.example.backend.controller;

@WebServlet("/verify")
public class VerifyController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        String signatureStr = request.getParameter("signature");
        HttpSession session = request.getSession();
        PublicKey publicKey = (PublicKey) session.getAttribute("publicKey");

        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            boolean isVerified = signature.verify(Base64.getDecoder().decode(signatureStr));
            request.setAttribute("result", isVerified ? "Signature is VALID" : "Signature is INVALID");
        } catch (Exception e) {
            request.setAttribute("result", "Verification failed: " + e.getMessage());
        }

        request.getRequestDispatcher("/verify.jsp").forward(request, response);
    }
}
