/**
 * 
 */
$(document).ready(function(){
	$("#register").click(function(){
		$("#main_auth").load("registerForm.jsp");
	});
	
	$("#login").click(function(){
		checkIt();
		if(status){
			var query = {id: $("#id").val(), passwd: $("#passwd").val()};
			
			$.ajax({
				type:"POST",
				url: "loginPro.jsp",
				data : query,
				success : function(data){
					alert(data);
					if(data == 1)
						$("#main_auth").load("loginForm.jsp");
					else if(data == 0){
						alert("비밀번호가 맞지 않습니다.");
						$("#passwd").val("");
						$("#passwd").focus();
					}else if(data == -1){
						alert("아이디가 맞지 않습니다.");
						$("#id").val("");
						$("#passwd").val("");
						$("#id").focus();
					}
				}
			});
		}
	});
	
	$("#update").click(function(){
		$("#main_auth").load("modify.jsp");
	});
	
	$("#logout").click(function(){
		$.ajax({
			type:"POST",
			url:"logout.jsp",
			success:function(data){
				$("#main_auth").load("loginForm.jsp");
			}
		});
	});
});

function checkIt(){
	status = true;
	if(!$.trim($("#id").val() )){
		alert("아이디를 입력하세여.");
		$("#id").focus();
		status = false;
		return false;
	}
	if(!$.trim($("#passwd").val() )){
		alert("비밀번호를 입력하세요");
		$("#passwd").focus();
		status = false;
		return false;
	}
}

