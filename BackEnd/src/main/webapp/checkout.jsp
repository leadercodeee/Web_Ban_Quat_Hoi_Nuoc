<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Checkout</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container my-5">
	<h1>Checkout</h1>
	<hr/>

	<form action="processCheckout.jsp" method="post">
		<!-- Shipping Information -->
		<h4>Billing Details</h4>
		<div class="row">
			<div class="col-md-6 mb-3">
				<label>First Name *</label>
				<input type="text" name="c_fname" class="form-control" required>
			</div>
			<div class="col-md-6 mb-3">
				<label>Last Name *</label>
				<input type="text" name="c_lname" class="form-control" required>
			</div>
			<div class="col-md-12 mb-3">
				<label>Company Name (Optional)</label>
				<input type="text" name="c_companyname" class="form-control">
			</div>
			<div class="col-md-12 mb-3">
				<label>Country *</label>
				<input type="text" name="c_country" class="form-control" required>
			</div>
			<div class="col-md-12 mb-3">
				<label>Address *</label>
				<input type="text" name="c_address" class="form-control" required>
			</div>
			<div class="col-md-6 mb-3">
				<label>State / Country *</label>
				<input type="text" name="c_state_country" class="form-control" required>
			</div>
			<div class="col-md-6 mb-3">
				<label>Postal / Zip *</label>
				<input type="text" name="c_postal_zip" class="form-control" required>
			</div>
			<div class="col-md-6 mb-3">
				<label>Email Address *</label>
				<input type="email" name="c_email_address" class="form-control" required>
			</div>
			<div class="col-md-6 mb-3">
				<label>Phone *</label>
				<input type="text" name="c_phone" class="form-control" required>
			</div>
		</div>

		<!-- Payment Method -->
		<h4>Payment Method</h4>
		<div class="mb-3">
			<label class="form-label">Choose a payment method *</label>
			<select name="payment_method" class="form-select" required>
				<option value="COD">Cash on Delivery</option>
				<option value="CreditCard">Credit Card</option>
				<option value="BankTransfer">Bank Transfer</option>
			</select>
		</div>

		<!-- Optional account creation -->
		<div class="form-check mb-3">
			<input class="form-check-input" type="checkbox" name="create_account" id="createAccountCheck">
			<label class="form-check-label" for="createAccountCheck">Create an account?</label>
		</div>
		<div class="mb-3" id="passwordField" style="display: none;">
			<label>Password</label>
			<input type="password" name="c_account_password" class="form-control">
		</div>

		<button type="submit" class="btn btn-success">Place Order</button>
	</form>
</div>

<script>
	// Hiển thị/ẩn mật khẩu khi tích chọn tạo tài khoản
	document.getElementById('createAccountCheck').addEventListener('change', function () {
		document.getElementById('passwordField').style.display = this.checked ? 'block' : 'none';
	});
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
