<div class="container">
    <jsp:include page="/sidebar"/>

    <div class=" single_top">
        <div class="single_grid">
            <div class="grid images_3_of_2">
                <ul id="etalage">
                    <li>
                        <a href="optionallink.html">
                            <img class="etalage_thumb_image" src="images/s4.jpg" class="img-responsive"/>
                            <img class="etalage_source_image" src="images/si4.jpg" class="img-responsive" title=""/>
                        </a>
                    </li>
                    <li>
                        <img class="etalage_thumb_image" src="images/s2.jpg" class="img-responsive"/>
                        <img class="etalage_source_image" src="images/si2.jpg" class="img-responsive" title=""/>
                    </li>
                    <li>
                        <img class="etalage_thumb_image" src="images/s3.jpg" class="img-responsive"/>
                        <img class="etalage_source_image" src="images/si3.jpg" class="img-responsive"/>
                    </li>
                    <li>
                        <img class="etalage_thumb_image" src="images/s1.jpg" class="img-responsive"/>
                        <img class="etalage_source_image" src="images/si1.jpg" class="img-responsive"/>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="desc1 span_3_of_2">


                <h4>item</h4>
                <div class="cart-b">
                    <div class="left-n ">$329.58</div>
                    <a class="now-get get-cart-in" href="#">ADD TO CART</a>
                    <div class="clearfix"></div>
                </div>
                <h6>100 items in stock</h6>
                <p>Details</p>
                <div class="share">
                    <h5>Share Product :</h5>
                    <ul class="share_nav">
                        <li><a href="#"><img src="../images/facebook.png" title="facebook"></a></li>
                        <li><a href="#"><img src="../images/twitter.png" title="Twiiter"></a></li>
                        <li><a href="#"><img src="../images/rss.png" title="Rss"></a></li>
                        <li><a href="#"><img src="../images/gpluse.png" title="Google+"></a></li>
                    </ul>
                </div>


            </div>
            <div class="clearfix"></div>
        </div>
        <ul id="flexiselDemo1">
            <li><img src="../images/pi.jpg"/>
                <div class="grid-flex"><a href="#">Bloch</a>
                    <p>Rs 850</p></div>
            </li>
            <li><img src="../images/pi1.jpg"/>
                <div class="grid-flex"><a href="#">Capzio</a>
                    <p>Rs 850</p></div>
            </li>
            <li><img src="../images/pi2.jpg"/>
                <div class="grid-flex"><a href="#">Zumba</a>
                    <p>Rs 850</p></div>
            </li>
            <li><img src="../images/pi3.jpg"/>
                <div class="grid-flex"><a href="#">Bloch</a>
                    <p>Rs 850</p></div>
            </li>
            <li><img src="../images/pi4.jpg"/>
                <div class="grid-flex"><a href="#">Capzio</a>
                    <p>Rs 850</p></div>
            </li>
        </ul>
        <script type="text/javascript">
		 $(window).load(function() {
			$("#flexiselDemo1").flexisel({
				visibleItems: 5,
				animationSpeed: 1000,
				autoPlay: true,
				autoPlaySpeed: 3000,    		
				pauseOnHover: true,
				enableResponsiveBreakpoints: true,
		    	responsiveBreakpoints: { 
		    		portrait: { 
		    			changePoint:480,
		    			visibleItems: 1
		    		}, 
		    		landscape: { 
		    			changePoint:640,
		    			visibleItems: 2
		    		},
		    		tablet: { 
		    			changePoint:768,
		    			visibleItems: 3
		    		}
		    	}
		    });
		    
		});








        </script>
        <div class="toogle">
            <h3 class="m_3">Product Details</h3>
            <p class="m_text"> Details</p>
        </div>
    </div>
</div>