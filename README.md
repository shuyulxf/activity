目录结构：
(1) src
	src__main
		 |__java
         |__resources
         |__webapp
               |__static
               |    |__css
               |    |__fonts
               |    |__images
               |    |__js
               |    |__scss
               |
               |__views
               |    |__actFormCompForRule
               |    |__widget
               |    |__components
               |    |__activity //活动列表页
               |    |__datas    //详细数据管理
               |    |__forms    //活动表单组件配置与管理
               |    |__prize    //系统自定义奖品
               |    |__ruleGroup //规则组管理与配置
               |    |__rules     //规则管理与配置
               |    |__statistic // 三个维度的数据统计
               |    |__system    // 系统配置
               |    |__errors    //出错时跳转的页面
               |
               |__WEB-INF
                    |__web.xml (java web的配置文件) 
    java文件夹是项目中所有的java文件
    resources中是spring框架所需要的所有的配置文件
    webapp是项目中和页面相关的所有源码，包括static和views。
    其中，static是所有的静态资源，包括css，字体文件，images，js,css等。
    views文件中是freemarker框架下的页面源码。
(2) mapper
    mybatis数据库框架的sql配置文件
(3) scripts
    脚本,后缀为.bat。保留文件夹
(4) WebContent
	java工程的默认配置文件，不需要修改
