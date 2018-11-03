var status = true;

$(document).ready(function(){
   $("#checkId").click(function(){
      if($("#id").val()){
         var query = {id:$("#id").val()};
         
         $.ajax({
            type:"POST",//요청방식
            url:"confirmId.jsp",//요청 페이지
            data:query,//파라미터
            success:function(data){//요청페이지처리에 성공시
               if(data == 1){
                  alert("사용할 수 없는 아이디");
                  $("#id").val("");
               }else if(data == -1)//사용할수있는아이디
                  alert("사용할 수 있는 아이디");
            }
         });
      }else{
         alert("사용할 아이디를 입력");
         $("#id").focus();
      }
   });
   
   $("#process").click(function(){
      checkIt();
      
      if(status){
         var query = {id:$("#id").val(),
            passwd:$("#passwd").val(),
            name:$("#name").val(),
            address:$("#address").val(),
            tel:$("#tel").val()};
         
         $.ajax({
            type:"POST",
            url:"registerPro.jsp",
            data:query,
            success:function(data){
              // window.location.href("main.jsp");
            	alert(data);
            	if(data > 0){
            		alert($("#id").val() + "가입을 축카 합니다.");
//            		alert("가입을 축카 합니다.");
            	}
            	else
            		alert("회원가입에 실패 했습니다.");
            	location.href="main.jsp";
            }
         });
      }
   });
   
   $("#cancle").click(function(){
      // window.location.href("main.jsp");
	   location.href="main.jsp";
   });
});
   
   function checkIt(){
      status = true;
      
      if(!$("#id").val()){
         alert("아이디를 입력하세요");
         $("#id").focus();
         status = false;
         return false;
      }
      
      if(!$("#passwd").val()){
         alert("비밀번호를 입력하세요");
         $("#repass").focus();
         status = false;
         return false;
      }
      
      if(!$("#name").val()){
         alert("사용자 이름을 입력하세요");
         $("#name").focus();
         status = false;
         return false;
      }
      
      if(!$("#address").val()){
         alert("주소를 입력하세요");
         $("#address").focus();
         status = false;
         return false;
      }
      
      if(!$("#tel").val()){
         alert("전화번호를 입력하세요");
         status = false;
         return false;
      }
   }