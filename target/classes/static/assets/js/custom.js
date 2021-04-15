$(document).ready(function() {

	$("#customSwitch1").on('change', function(){
		$(".plan_price").toggleClass("hide");
	});

	$('.banner_slider_1').owlCarousel({
	    animateOut: 'fadeOut',
	    loop:true,
	    margin:10,
	    nav:false,
	 	dots:true,
	    responsive:{
	        0:{
	            items:1
	        },
	        600:{
	            items:1
	        },
	        1000:{
	            items:1
	        }
	    }
	});

	$('.partner_carousel').owlCarousel({
	    loop:true,
	    margin:10,
	    nav:false,
	    autoplay:true,
	    responsive:{
	        0:{
	            items:2
	        },
	        600:{
	            items:4
	        },
	        1000:{
	            items:5
	        }
	    }
	});




       if ($(window).width() > 768) {  

	         $('.js-example-basic-multiple').select2();
    		$('.js-example-basic-single').select2();  
   

       }   
		     




   	

    $(window).on("scroll", function(){
		var scroll = jQuery(window).scrollTop();
		if(scroll >= 100){
			$(".header_menu").addClass("stick")
		}
		else{
			$(".header_menu").removeClass("stick")	
		}
    });
    

	$(".hamburger ").on("click", function(){
  		$(this).toggleClass("is-active");
  		$(this).next("ul").slideToggle();
	});	

	$(".filter_btn").on("mouseenter",function(){
		$(this).toggleClass("active");
		$(".filter_option").slideToggle();
	});
	
	$(".fzf_page .form-control").on("focus", function(){
   	  $(".fzf_page").addClass("focused");
	});

	$(".fzf_page .form-control").blur("focus", function(){
   	  $(".fzf_page").removeClass("focused");
	});

	/*$(".fzf_page").click(function(){
  		$(this).removeClass("focused");
	});*/
	
});


	$(window).on("resize load", function(){
       if ($(window).width() <= 992) {  

	           $(".has-sub-menu").on("click",function(){
              	$( this ).children(".sub-menu").slideToggle();
              });

               $(".login_pop > .btn").on("click",function(){
               	$(this).parent(".login_pop").siblings(".login_pop").children(".login_pop_box").fadeOut();
               	$(this).next(".login_pop_box").fadeToggle();
               });	
   

       }   
		     
	});	


window.onload = function () {

//Better to construct options first and then pass it as a parameter
var options = {
	title: {
		text: " Your Profile Views"
	},
	animationEnabled: true,
	exportEnabled: true,
	data: [
	{
		type: "spline", //change it to line, area, column, pie, etc
		dataPoints: [
			{ x: 10, y: 400 },
			{ x: 20, y: 12 },
			{ x: 30, y: 8 },
			{ x: 40, y: 14 },
			{ x: 50, y: 6 },
			{ x: 60, y: 24 },
			{ x: 70, y: -4 },
			{ x: 80, y: 10 }
		]
	}
	]
};


}

/*scroll animation*/
AOS.init({
    delay: 100,
    duration: 1000
});
/*end scroll animation*/