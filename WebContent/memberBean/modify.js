
$(document).ready(
		function() {
			$("#modify").click(
					function() {
						var query = {
							passwd : $("#passwd").val()
						};

						$.ajax({
							type : "post",
							url : "memberCheck.jsp",
							data : query,
							success : function(data) {
								if (data == 1)
									$("#main_auth").load(
											"modifyForm.jsp?passwd="
													+ $("#passwd").val());
								else {
									alert("비밀번호가 맞지 않습니다.");
									$("#passwd").val("");
									$("#passwd").focus();
								}
							}
						});
					});

			$("#modifyProcess").click(function() {
				// checkIt();
				var signal = checkIt();
				if (signal == true) {
					var query = {
						id : $("#id").val(),
						passwd : $("#passwd").val(),
						name : $("#name").val(),
						address : $("#address").val(),
						tel : $("#tel").val()
					};

					$.ajax({
						type : "post",
						url : "modifyPro.jsp",
						data : query,
						success : function(data) {
							alert(data);
							if (data == 1) {
								alert($("#id").val() + "회원정보가 수정되었습니다.");
								// window.location.href("main.jsp");
								location.href = "main.jsp";
							} else
								alert("수정 안됨 시발");
						}
					});
				}else
					alert("fail..");
			});

			$("#cancle").click(function() {
				// window.location.href("main.jsp");
				location.href = "main.jsp";
			});

			$("#delete").click(function() {
				var query = {
					passwd : $("#passwd").val()
				};

				$.ajax({
					type : "post",
					url : "memberCheck.jsp",
					data : query,
					success : function(data) {
						if (data == 1) {
							$.ajax({
								type : "POST",
								url : "deletePro.jsp",
								data : query,
								success : function(data) {
									if (data == 1) {
										alert("회원 탈퇴가 되었습니다.");
										$("#main_auth").load("loginForm.jsp");
									}
								}
							});
						} else {
							alert("비밀번호가 맞지 않습니다.");
							$("#passwd").val("");
							$("#passwd").focus();
						}
					}
				});
			});
		});

function checkIt() {
	var status = true;
	if ($("#passwd").val() != $("#repass").val()) {
		alert("비밀번호 안맞어");
		status = false;
	}
	else
		status = true;
	return status;
}