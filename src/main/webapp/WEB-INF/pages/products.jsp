<div class="container">
    <div class="sub-cate">
        <div class=" top-nav rsidebar span_1_of_left">
            <h3 class="cate">FILTER</h3>
            <ul class="menu">
                <form id="filter-form" method="get" action="products">
                    <li class="item1">
                        <a href="#">Category<img class="arrow-img" src="images/arrow1.png" alt=""/> </a>
                        <ul class="cute">
                            <c:forEach items="${allCategories}" var="category">
                                <li class="subitem1">
                                    <input type="checkbox" name="category-checkbox" value="${category.id}"
                                    <c:if test="${  fn:contains( filter.categories, category.id ) }">checked</c:if>
                                    >
                                    ${category.name}
                                </li>
                            </c:forEach>

                        </ul>
                    </li>

                    <li class="item2"><a href="#">Manufacturer<img class="arrow-img" src="images/arrow1.png" alt=""/>
                    </a>
                        <ul class="cute">
                            <c:forEach items="${allManufacturers}" var="manufacturer">
                                <li class="subitem1">
                                    <input type="checkbox" name="manufacturer-checkbox" value="${manufacturer.id}"
                                    <c:if test="${  fn:contains( filter.manufacturers, manufacturer.id ) }">checked
                                    </c:if>
                                    />
                                    ${manufacturer.name}
                                </li>
                            </c:forEach>
                        </ul>
                    </li>

                    <li class="menu-price-left">

                        <div id="price">
                            <b> Price: </b>

                            <output name="priceFromOutputName" id="priceFromOutputId">${filter.minPrice}</output>
                            <input type="range" name="minPrice" id="priceFromInputId" value="${filter.minPrice}" min="0"
                                   max="10000"
                                   oninput="priceFromOutputId.value = priceFromInputId.value"><br>


                            <output name="priceToOutputName" id="priceToOutputId">${filter.maxPrice}</output>
                            <input type="range" name="maxPrice" id="priceToInputId" value="${filter.maxPrice}" min="0"
                                   max="10000"
                                   oninput="priceToOutputId.value = priceToInputId.value">


                            <input id="filter-submit" type="submit" value="filter">

                        </div>

                    </li>
                </form>

            </ul>
        </div>

    </div>

    <div class="women-product">
        <div class=" w_content">
            <div class="women">
                <a href="#"><h4>Found - <span>${ countProducts } itemms</span></h4></a>
                <ul class="w_nav">
                    <li><b>Items on page : &#8199;</b></li>
                    <li><a href="${baseURL}/products?limit=9"
                        <c:if test="${ filter.productLimit == 9 }">class = "selectedLink"</c:if>
                        >9</a></li>
                    |
                    <li><a href="${baseURL}/products?limit=18"
                        <c:if test="${ filter.productLimit == 18 }">class = "selectedLink"</c:if>
                        >18 </a></li>
                    |
                    <li><a href="${baseURL}/products?limit=90"
                        <c:if test="${ filter.productLimit == 90 }">class = "selectedLink"</c:if>
                        >90</a></li>

                    <li><b>&#8199;Price:&#8199;</b></li>
                    <li><a href="${baseURL}/products?orderBy=price&desc=true"
                        <c:if test="${ filter.orderBy eq 'PRICE' && filter.descendingly == true}">class =
                            "selectedLink"
                        </c:if>
                        >High</a></li>
                    |
                    <li><a href="${baseURL}/products?orderBy=price&desc=false"
                        <c:if test="${ filter.orderBy eq 'PRICE' && filter.descendingly == false}">class =
                            "selectedLink"
                        </c:if>
                        >Low</a></li>

                    <li><b>&#8199;Name:&#8199;</b></li>
                    <li><a href="${baseURL}/products?orderBy=name&desc=false"
                        <c:if test="${ filter.orderBy eq 'NAME' && filter.descendingly == false}">class =
                            "selectedLink"
                        </c:if>
                        >A-Z</a></li>
                    |
                    <li><a href="${baseURL}/products?orderBy=name&desc=true"
                        <c:if test="${ filter.orderBy eq 'NAME' && filter.descendingly == true}">class =
                            "selectedLink"
                        </c:if>
                        >Z-A</a></li>
                    <div class="clearfix"></div>
                </ul>
                <div class="clearfix"></div>
            </div>
        </div>
        <!-- grids_of_4 -->
        <div class="grid-product">

            <c:forEach items="${filteredProducts}" var="product">
                <div class="  product-grid">
                    <div class="content_box"><a href="single.html">
                        <div class="left-grid-view grid-view-left">
                            <img src="images/${product.image}" class="img-responsive watch-right" alt=""/>
                            <div class="mask">
                                <div class="info">Quick View</div>
                            </div>
                        </div>
                    </a>
                        <h4><a href="#">${product.name}</a></h4>
                        <p>${product.description}</p>
                        ${product.price} $
                    </div>
                </div>
            </c:forEach>

            <div class="clearfix"></div>
        </div>
        <div>
            <c:if test="${countProducts > filter.productLimit}">

                <b> Pages :</b>
                <c:forEach var="i" begin="1"
                           end="${countProducts /filter.productLimit + (countProducts % filter.productLimit == 0 ? 0 : 1)}">

                    &#8199;<a href="products?page=${i -1}">
                    <c:out value="${i}"/>
                </a>
                </c:forEach>
            </c:if>


        </div>
    </div>

    <div class="clearfix"></div>
</div>
