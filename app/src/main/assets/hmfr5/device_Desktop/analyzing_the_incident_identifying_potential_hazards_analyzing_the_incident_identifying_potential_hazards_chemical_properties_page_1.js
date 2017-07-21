if (window.VarCurrentView) VarCurrentView.set('Desktop');
function init_Desktop() {
	if ( rcdObj.view != 'Desktop' ) return;
	if( is.min ){
		trivPageTracking.InitPageTracking();
		if( parent.document != document) parent.document.title=document.title
		if( bInitSCORM )
		{
		  var oldLoc = AICC_Lesson_Location.getValue()
		  if( oldLoc && oldLoc.indexOf( '.' ) > 0 && oldLoc != 'analyzing_the_incident_identifying_potential_hazards_analyzing_the_incident_identifying_potential_hazards_chemical_properties_page_1.html' && oldLoc != 'A002index.html' )
		  {
		    var bmCallback = function(go){
		      if(go){
		       saveVariable( 'TrivantisEPS', 'T');
		       ObjLayerActionGoTo( oldLoc );
		    }};
		    var mbconf = new jsTrivDlgMsgBox( 'prevLocation1399', 'hmfr5_chapter_4_section_3.html','You have previously been in this lesson. Would you like to return to the last visited location in the lesson?', 1, function(e,rtn){ bmCallback(rtn==0)} ); mbconf.create();
		  }
		}

		if( window.name.indexOf( 'Trivantis_' ) == -1 )
		  AICC_Lesson_Location.set( 'analyzing_the_incident_identifying_potential_hazards_analyzing_the_incident_identifying_potential_hazards_chemical_properties_page_1.html' )
		trivProtectContent();
		trivCenterPage = true;
		trivCenter();
		window.trivBgndAudio = new jsBgSound();
		trivBgndAudio.attach(false);
		attachObjs();

		og2000.cwObj={};
		og2000.cwObj.arChld=[
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction0for2000();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction1for2000();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction2for2000();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction3for2000();
    }}];
		og2005.cwObj={};
		og2005.cwObj.arChld=[
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction0for2005();
    }}];
		og1993.cwObj={};
		og1993.cwObj.arChld=[
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction0for1993();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction1for1993();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction2for1993();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction3for1993();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction4for1993();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction5for1993();
    }}];
		og1981.cwObj={};
		og1981.cwObj.arChld=[
    ];
		og1974.cwObj={};
		og1974.cwObj.arChld=[
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction0for1974();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction1for1974();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction2for1974();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction3for1974();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction4for1974();
    }}];
		og1383.cwObj={};
		og1383.cwObj.arChld=[
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction0for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction1for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction2for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction3for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction4for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction5for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction6for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction7for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction8for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction9for1383();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction10for1383();
    }}];
		og1395.cwObj={};
		og1395.cwObj.arChld=[
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction0for1395();
    }},
    {type:6,on:1001,delay:0, actItem:function(){
        grpAction1for1395();
    }}];
		if(VarWrongAnswer.equals('0')){
{og1974.issueActions(1001);}
}
else {og1993.issueActions(1001);}
    if(typeof pF == 'function') pF();
textbutton1984.hide();
		trivPage.issueShowActions(arWnds);
		trivArExec(arWnds,function(wnd){ wnd.issueActions(11); });
		if ( trivPageTracking.GetRangeStatus(1399) == 'notstarted' ) trivPageTracking.SetRangeStatus(1399,2);
		trivPage.actionMute();
	}
}
function defineFuncs_Desktop() {
	if ( rcdObj.view != 'Desktop' ) return;
	try{
		if (window.initGEV)
		{
		 initGEV(0,swipeLeft,swipeRight);

		}
		} catch(e) { if (window.console) window.console.log(e); }	pageClick = n;
	pageRClick = n;
	pageKey = n;
}
text2146.rcdData.att_Desktop = 
{
	innerHtml:	"<div name=\"dCon\" style=\"position: absolute; left: 0px; top: 0px; width: 171px; min-height: 21px;\"><div name=\"dCon2\" class=\"ttxt\" style=\"left: 0px; top: 0px; width: 171px; min-height: 21px;\"><p style=\"text-align: right;\"><span style=\"font-family:\'Century Gothic\',sans-serif;color:#000000;font-size:10pt;\">Page</span></p></div></div>",
	cssText:	"visibility: inherit; position: absolute; left: 791px; top: 48px; width: 171px; height: 21px; z-index: 0;",
	cssClasses:	"",
	htmlId:		"tobj2146",
	bInsAnc:	0,
	cwObj:		{
				"arChld":
	[
		{type:6,on:11,delay:0, actItem:function(){ if(!VarCurrentSectionName.equals('~~~null~~~'))text2146.changeContents( "Page " +  VarPageInSection.getValueForDisplay() + " of " +  VarPagesInSection.getValueForDisplay() + "" ); else if(typeof pF == 'function') pF();
    if(typeof pF == 'function') pF(); }},
		{type:6,on:11,delay:0, actItem:function(){ if(!VarCurrentChapterName.equals('~~~null~~~')&&VarCurrentSectionName.equals('~~~null~~~'))text2146.changeContents( "Page " +  VarPageInChapter.getValueForDisplay() + " of " +  VarPagesInChapter.getValueForDisplay() + "" ); else if(typeof pF == 'function') pF();
    if(typeof pF == 'function') pF(); }},
		{type:6,on:11,delay:0, actItem:function(){ if(VarCurrentChapterName.equals('~~~null~~~')&&VarCurrentSectionName.equals('~~~null~~~'))text2146.changeContents( "Page " +  VarPageInTitle.getValueForDisplay() + " of " +  VarPagesInTitle.getValueForDisplay() + "" ); else if(typeof pF == 'function') pF();
    if(typeof pF == 'function') pF(); }}
	]
			},
	objData:	{"a":[0,32,0,[791,48,171,21]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":791,"y":48,"width":171,"height":21},"dwTextFlags":0,"marginSize":0}
};
og2000.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og2000",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
og2005.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og2005",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
og1993.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og1993",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
text1992.rcdData.att_Desktop = 
{
	innerHtml:	"<div name=\"dCon\" style=\"position: absolute; left: 0px; top: 0px; width: 919px; min-height: 21px;\"><div name=\"dCon2\" class=\"ttxt\" style=\"left: 0px; top: 0px; width: 919px; min-height: 21px;\"><p style=\"text-align:left\"><span style=\"color: rgb(210, 3, 8); font-family: &quot;century gothic&quot;, sans-serif; font-size:12pt;\">myCurrentChapterName</span></p></div></div>",
	cssText:	"visibility: inherit; position: absolute; left: 45px; top: 48px; width: 919px; height: 21px; z-index: 1;",
	cssClasses:	"",
	htmlId:		"tobj1992",
	bInsAnc:	0,
	objData:	{"a":[0,32,0,[45,48,919,21]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":45,"y":48,"width":919,"height":21},"dwTextFlags":0,"marginSize":0}
};
text1991.rcdData.att_Desktop = 
{
	innerHtml:	"<div name=\"dCon\" style=\"position: absolute; left: 0px; top: 0px; width: 919px; min-height: 22px;\"><div name=\"dCon2\" class=\"ttxt\" style=\"left: 0px; top: 0px; width: 919px; min-height: 22px;\"><p style=\"text-align:left\"><span style=\"color:#000000;font-family:century gothic,sans-serif;font-size:10pt;\">myCurrentSectionName</span></p></div></div>",
	cssText:	"visibility: inherit; position: absolute; left: 46px; top: 72px; width: 919px; height: 22px; z-index: 2;",
	cssClasses:	"",
	htmlId:		"tobj1991",
	bInsAnc:	0,
	objData:	{"a":[0,32,0,[46,72,919,22]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":46,"y":72,"width":919,"height":22},"dwTextFlags":0,"marginSize":0}
};
shape1990.rcdData.att_Desktop = 
{
	innerHtml:	"<svg style=\"pointer-events: none; left: 0px; top: 0px; width: 100%; height: 100%; position: absolute;\" viewBox=\"0 0 920 2\" preserveAspectRatio=\"none\"><g transform=\"translate(460 1)\" style=\"\">\n	<path d=\"M 0 0 L 919 1\" style=\"stroke: rgb(174, 170, 170); stroke-width: 1; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(174, 170, 170); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100); pointer-events: auto;\" transform=\"translate(0 0) translate(-459.5, -0.5) \" stroke-linecap=\"round\"></path>\n</g>\n	<g transform=\"translate(460 7.5)\">\n		<text font-family=\"\'Eurostile LT Std Ext2\',Eurostile\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100);\">\n			<tspan x=\"0\" y=\"5.04\" fill=\"#000000\"></tspan>\n		</text>\n	</g>\n</svg>",
	cssText:	"visibility: inherit; position: absolute; left: 44.5px; top: 69.5px; width: 920px; height: 2px; z-index: 3; overflow: visible; pointer-events: none;",
	cssClasses:	"",
	htmlId:		"tobj1990",
	bInsAnc:	0,
	objData:	{"a":[0,32,0,[44.5,69.50000000000011,920,2]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":45,"y":70,"width":920,"height":2},"btnState":"disabled","altValue":"Line","titleValue":"Line","fallbackImg":"<img src=\"images/desktop_shape1990.png\" style=\"position: absolute; width: 100%; height: 100%; top: 0px; left: 0px;\">"}
};
og1981.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og1981",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
textbutton1982.rcdData.att_Desktop = 
{
	innerHtml:	"<svg style=\"pointer-events: none; left: 0px; top: 0px; width: 100%; height: 100%; position: absolute;\" viewBox=\"0 0 119 40\" preserveAspectRatio=\"none\"><g transform=\"translate(59.5 20)\" style=\"\">\n	<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100); pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n	<g transform=\"translate(59.5 20)\">\n		<text font-family=\"\'Century Gothic\',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100);\">\n			<tspan x=\"-18.49\" y=\"5.04\" fill=\"#000000\">NEXT</tspan>\n		</text>\n	</g>\n</svg>",
	cssText:	"visibility: inherit; position: absolute; left: 890px; top: 622px; width: 119px; height: 40px; z-index: 6; cursor: pointer; overflow: visible; pointer-events: none;",
	cssClasses:	"",
	htmlId:		"tobj1982",
	bInsAnc:	1,
	cwObj:		{
				"arChld":
	[
		{type:6,on:2,delay:0, actItem:function(){ trivExitPage('analyzing_the_incident_identifying_potential_hazards_analyzing_the_incident_identifying_potential_hazards_chemical_properties_page_2.html',true);
    if(typeof pF == 'function') pF(); }}
	]
			},
	objData:	{"a":[4,32864,0,[890,622,119,40]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":890,"y":622,"width":119,"height":40},"imgDataNormal":"images/desktop_shape1982.png","imgDataOver":"images/desktop_shape1982_over.png","imgDataDown":"images/desktop_shape1982_down.png","imgDataDisabled":"images/desktop_shape1982_disabled.png","svgDataNormal":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-18.49\" y=\"5.04\" fill=\"#000000\">NEXT</tspan>\n\t\t</text>\n\t</g>\n","svgDataOver":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(194, 194, 194); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-18.49\" y=\"5.04\" fill=\"#000000\">NEXT</tspan>\n\t\t</text>\n\t</g>\n","svgDataDown":"<g transform=\"translate(60 20.5)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 1; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(129, 129, 129); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(60 20.5)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-18.49\" y=\"5.04\" fill=\"#000000\">NEXT</tspan>\n\t\t</text>\n\t</g>\n","svgDataDisabled":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity: 0.6; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 0.6;\">\n\t\t\t<tspan x=\"-18.49\" y=\"5.04\" fill=\"#000000\">NEXT</tspan>\n\t\t</text>\n\t</g>\n","btnState":"enabled","altValue":"NextButton","titleValue":"NextButton","fallbackImg":"<img style=\"position: absolute; width: 100%; height: 100%; top: 0px; left: 0px;\">"}
};
textbutton1984.rcdData.att_Desktop = 
{
	innerHtml:	"<svg style=\"pointer-events: none; left: 0px; top: 0px; width: 100%; height: 100%; position: absolute;\" viewBox=\"0 0 119 40\" preserveAspectRatio=\"none\"><g transform=\"translate(59.5 20)\" style=\"\">\n	<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100); pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n	<g transform=\"translate(59.5 20)\">\n		<text font-family=\"\'Century Gothic\',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100);\">\n			<tspan x=\"-37.49\" y=\"5.04\" fill=\"#000000\">PREVIOUS</tspan>\n		</text>\n	</g>\n</svg>",
	cssText:	"visibility: inherit; position: absolute; left: -7.10543e-15px; top: 622px; width: 119px; height: 40px; z-index: 7; cursor: pointer; overflow: visible; pointer-events: none;",
	cssClasses:	"",
	htmlId:		"tobj1984",
	bInsAnc:	1,
	cwObj:		{
				"arChld":
	[
		{type:6,on:2,delay:0, actItem:function(){ false }}
	]
			},
	objData:	{"a":[4,32864,0,[-7.105427357601002e-15,622,119,40]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":0,"y":622,"width":119,"height":40},"imgDataNormal":"images/desktop_shape1984.png","imgDataOver":"images/desktop_shape1984_over.png","imgDataDown":"images/desktop_shape1984_down.png","imgDataDisabled":"images/desktop_shape1984_disabled.png","svgDataNormal":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-37.49\" y=\"5.04\" fill=\"#000000\">PREVIOUS</tspan>\n\t\t</text>\n\t</g>\n","svgDataOver":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(194, 194, 194); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-37.49\" y=\"5.04\" fill=\"#000000\">PREVIOUS</tspan>\n\t\t</text>\n\t</g>\n","svgDataDown":"<g transform=\"translate(60 20.5)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 1; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(129, 129, 129); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(60 20.5)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-37.49\" y=\"5.04\" fill=\"#000000\">PREVIOUS</tspan>\n\t\t</text>\n\t</g>\n","svgDataDisabled":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity: 0.6; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 0.6;\">\n\t\t\t<tspan x=\"-37.49\" y=\"5.04\" fill=\"#000000\">PREVIOUS</tspan>\n\t\t</text>\n\t</g>\n","btnState":"enabled","altValue":"PreviousButton","titleValue":"PreviousButton","fallbackImg":"<img style=\"position: absolute; width: 100%; height: 100%; top: 0px; left: 0px;\">"}
};
textbutton1988.rcdData.att_Desktop = 
{
	innerHtml:	"<svg style=\"pointer-events: none; left: 0px; top: 0px; width: 100%; height: 100%; position: absolute;\" viewBox=\"0 0 119 40\" preserveAspectRatio=\"none\"><g transform=\"translate(59.5 20)\" style=\"\">\n	<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100); pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n	<g transform=\"translate(59.5 20)\">\n		<text font-family=\"\'Century Gothic\',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity:1;filter:alpha(opacity=100);\">\n			<tspan x=\"-41.14\" y=\"-5.45\" fill=\"#000000\">RETURN TO</tspan>\n			<tspan x=\"-38.57\" y=\"15.53\" fill=\"#000000\">QUESTION</tspan>\n		</text>\n	</g>\n</svg>",
	cssText:	"visibility: hidden; position: absolute; left: 444px; top: 622px; width: 119px; height: 40px; z-index: 8; cursor: pointer; overflow: visible; pointer-events: none;",
	cssClasses:	"",
	htmlId:		"tobj1988",
	bInsAnc:	1,
	cwObj:		{
				"arChld":
	[
		{type:6,on:2,delay:0, actItem:function(){ trivExitPage('history.back()'); 
    if(typeof pF == 'function') pF(); }}
	]
			},
	objData:	{"a":[4,32832,0,[444,622,119,40]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":444,"y":622,"width":119,"height":40},"imgDataNormal":"images/desktop_shape1988.png","imgDataOver":"images/desktop_shape1988_over.png","imgDataDown":"images/desktop_shape1988_down.png","imgDataDisabled":"images/desktop_shape1988_disabled.png","svgDataNormal":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-41.14\" y=\"-5.45\" fill=\"#000000\">RETURN TO</tspan>\n\t\t\t<tspan x=\"-38.57\" y=\"15.53\" fill=\"#000000\">QUESTION</tspan>\n\t\t</text>\n\t</g>\n","svgDataOver":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(194, 194, 194); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-41.14\" y=\"-5.45\" fill=\"#000000\">RETURN TO</tspan>\n\t\t\t<tspan x=\"-38.57\" y=\"15.53\" fill=\"#000000\">QUESTION</tspan>\n\t\t</text>\n\t</g>\n","svgDataDown":"<g transform=\"translate(60 20.5)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 1; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(129, 129, 129); fill-rule: nonzero; opacity: 1; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(60 20.5)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;\">\n\t\t\t<tspan x=\"-41.14\" y=\"-5.45\" fill=\"#000000\">RETURN TO</tspan>\n\t\t\t<tspan x=\"-38.57\" y=\"15.53\" fill=\"#000000\">QUESTION</tspan>\n\t\t</text>\n\t</g>\n","svgDataDisabled":"<g transform=\"translate(59.5 20)\" style=\"\">\n\t<path d=\"M 0 0 L 119 0 A 0 0 0 0 1 119 0 L 119 40 A 0 0 0 0 1 119 40 L 0 40 A 0 0 0 0 1 0 40 L 0 0 A 0 0 0 0 1 0 0 Z\" style=\"stroke: rgb(0, 0, 0); stroke-width: 0; stroke-dasharray: none; stroke-linecap: round; stroke-linejoin: round; stroke-miterlimit: 10; fill: rgb(162, 162, 162); fill-rule: nonzero; opacity: 0.6; pointer-events: auto;\" transform=\"translate(0 0) translate(-59.5, -20) \" stroke-linecap=\"round\"></path>\n</g>\n\t<g transform=\"translate(59.5 20)\">\n\t\t<text font-family=\"'Century Gothic',sans-serif\" font-size=\"15.9999996\" font-weight=\"normal\" style=\"stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 0.6;\">\n\t\t\t<tspan x=\"-41.14\" y=\"-5.45\" fill=\"#000000\">RETURN TO</tspan>\n\t\t\t<tspan x=\"-38.57\" y=\"15.53\" fill=\"#000000\">QUESTION</tspan>\n\t\t</text>\n\t</g>\n","btnState":"enabled","altValue":"ReturnButton","titleValue":"ReturnButton","fallbackImg":"<img style=\"position: absolute; width: 100%; height: 100%; top: 0px; left: 0px;\">"}
};
og1974.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og1974",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
og1383.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og1383",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
og1395.rcdData.att_Desktop = 
{
	innerHtml:	"",
	cssText:	"",
	cssClasses:	"",
	htmlId:		"og1395",
	bInsAnc:	undefined,
	objData:	{"a":[0,32,0,[]]}
};
text1398.rcdData.att_Desktop = 
{
	innerHtml:	"<div name=\"dCon\" style=\"position: absolute; left: 0px; top: 0px; width: 449px; height: 102px;\"><div id=\"dCon3\" style=\"position: absolute; background-color: rgb(182, 215, 168); background-clip: content-box; left: 0px; top: 0px; height: 58px; width: 405px; border: 22px solid rgb(255, 255, 255);\"></div><div name=\"dCon2\" class=\"ttxt\" style=\"left: 22px; top: 22px; width: 405px; height: 58px;\"><p style=\"text-align: center;\">&nbsp;</p><p style=\"text-align: center;\"><strong><span style=\"font-size:16pt; font-family: &quot;century gothic&quot;, sans-serif;\">Correct!</span></strong></p></div></div>",
	cssText:	"visibility: hidden; position: absolute; left: 276px; top: 277px; width: 449px; height: 102px; z-index: 9;",
	cssClasses:	"",
	htmlId:		"tobj1398",
	bInsAnc:	0,
	objData:	{"a":[0,64,0,[276,277,449,102]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":22,"lineStyle":0,"borderColor":"#ffffff"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":276,"y":277,"width":449,"height":102},"dwTextFlags":0,"marginSize":0}
};
text2171.rcdData.att_Desktop = 
{
	innerHtml:	"<div name=\"dCon\" style=\"position: absolute; left: 0px; top: 0px; width: 418px; min-height: 29px;\"><div name=\"dCon2\" class=\"ttxt\" style=\"left: 0px; top: 0px; width: 418px; min-height: 29px;\"><p style=\"text-align: center;\"><span style=\"font-size:16pt;\"><strong><span style=\"font-family: century gothic,sans-serif;\">Chemical Properties</span></strong></span></p></div></div>",
	cssText:	"visibility: inherit; position: absolute; left: 296px; top: 113px; width: 418px; height: 29px; z-index: 4;",
	cssClasses:	"",
	htmlId:		"tobj2171",
	bInsAnc:	0,
	objData:	{"a":[0,32,0,[296,113,418,29]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":296,"y":113,"width":418,"height":29},"dwTextFlags":0,"marginSize":0}
};
text1400.rcdData.att_Desktop = 
{
	innerHtml:	"<div name=\"dCon\" style=\"position: absolute; left: 0px; top: 0px; width: 613px; min-height: 416px;\"><div name=\"dCon2\" class=\"ttxt\" style=\"left: 0px; top: 0px; width: 613px; min-height: 416px;\"><p style=\"text-align:left\"><span style=\"font-family: &quot;Century Gothic&quot;, sans-serif; font-size: 16px;\">Chemical properties describe the chemical nature of a material and the behaviors and interactions that occur at a molecular level. &nbsp;While not always grouped with other chemical properties, toxicity and biological hazards will also be addressed in this lesson. &nbsp;This lesson also explains the following important chemical properties in order of commonality at incidents:</span></p><ul style=\"padding-left: 40px; -webkit-padding-start: 40px;\"><li style=\"text-align: left; font-family: &quot;Century Gothic&quot;, sans-serif; font-size: 16pt; color: rgb(0, 0, 0); font-weight: normal; font-style: normal;\"><span style=\"font-family:century gothic,sans-serif;\"><span id=\"trivUnsetLiStyle\" style=\"font-weight: normal; font-style: normal; color: rgb(0, 0, 0); font-size: 16px;\">Flammability</span></span></li><li style=\"text-align: left; font-family: &quot;Century Gothic&quot;, sans-serif; font-size: 12pt; color: rgb(0, 0, 0); font-weight: normal; font-style: normal;\"><span style=\"font-family: &quot;century gothic&quot;, sans-serif; color: rgb(0, 0, 0); font-size: 12pt;\">Corrosivity</span></li><li style=\"text-align: left; font-family: &quot;Century Gothic&quot;, sans-serif; font-size: 12pt; color: rgb(0, 0, 0); font-weight: normal; font-style: normal;\"><span id=\"trivUnsetLiStyle\" style=\"font-weight: normal; font-style: normal; color: rgb(0, 0, 0); font-family: &quot;century gothic&quot;, sans-serif; font-size: 12pt;\"><span style=\"font-weight: normal; font-style: normal; color: rgb(0, 0, 0); font-size: 12pt;\">Reactivity</span></span></li><li style=\"text-align: left; font-family: &quot;Century Gothic&quot;, sans-serif; font-size: 12pt; color: rgb(0, 0, 0); font-weight: normal; font-style: normal;\"><span id=\"trivUnsetLiStyle\" style=\"font-weight:normal;font-style:normal;color:#000000;font-family:Arial,sans-serif;font-size:12pt\"><span style=\"font-family: &quot;century gothic&quot;, sans-serif; color: rgb(0, 0, 0); font-size: 12pt;\">Radioactivity</span></span></li></ul></div></div>",
	cssText:	"visibility: inherit; position: absolute; left: 198px; top: 193px; width: 613px; height: 416px; z-index: 5;",
	cssClasses:	"",
	htmlId:		"tobj1400",
	bInsAnc:	0,
	objData:	{"a":[0,32,0,[198,193,613,416]],"borderEffect":{"outline":0,"outlineColor":"#000000","borderWeight":0,"lineStyle":0,"borderColor":"#000000"},"rotateEffect":{"angle":0,"anchorX":50,"anchorY":50},"desktopRect":{"x":198,"y":193,"width":613,"height":416},"dwTextFlags":0,"marginSize":0}
};
rcdObj.pgWidth_Desktop = pgWidth_desktop;
rcdObj.preload_Desktop = [];
rcdObj.pgStyle_Desktop = 'position: absolute; left: 50%; top: 0px; width: 1009px; height: 662px; overflow: hidden; margin-left: -504.5px; background-size: auto auto;'
rcdObj.backgrd_Desktop = ["#FFFFFF","",0,0,1];
