<%--
  Created by IntelliJ IDEA.
  User: hongh
  Date: 23/05/2025
  Time: 10:45 CH
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>Verify Signature</title></head>
<body>
<h2>Verify Signature</h2>
<form action="verify" method="post">
    <textarea name="data" rows="5" cols="40" placeholder="Enter original data"></textarea><br>
    <textarea name="signature" rows="5" cols="40" placeholder="Paste signature (Base64)"></textarea><br>
    <input type="submit" value="Verify">
</form>
<p>${result}</p>
</body>
</html>
