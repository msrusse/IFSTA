﻿
		/*
 * Copyright (C) 2007 Trivantis Corporation
 */

	
function saveVariable(A,B,C,D,E,F){convertCookies(D,F);if (typeof (Storage)!=="undefined"&&window.localStorage){var G=getTitleMgrHandle();var H="Lectora"+(D?(":"+Encode(D)):"");var I;if (C) I=localStorage.getItem(H);else I=sessionStorage.getItem(H);var J=false;var K="";if (I){var L=I.split(",");for (var i=0;i<L.length;i++){if (L[i].length==0) continue;var M=L[i].split('=');if (M.length==2){var n=unescapeDelim(M[0]);var v=unescapeDelim(M[1]);if (n==A){J=true;v=B;};K+=(escapeDelim(n)+'='+escapeDelim(v)+',');}}};if (!J){K+=(escapeDelim(A)+'='+escapeDelim(B)+',');};if (C) localStorage.setItem(H,K);else sessionStorage.setItem(H,K);if (!F) trivLogMsg('saveVariable for '+A+' to ['+B+']',2);if (G){G.setVariable(A,B,C);if ((!C||E)&&!document.TitleMgr&&!window.jTitleManager) return;}}else saveVariableInCookie(A,B,C,D,E,F);};function saveVariableInCookie(A,B,C,D,E,F){var G=getTitleMgrHandle();var H="; path=/";if (C){var I=new Date();I.setTime(I.getTime()+(C*24*60*60*1000));H+="; expires="+I.toGMTString();};var J=Encode(A);var K=Encode(B);var L=(C?'~LectoraPermCookie':'~LectoraTempCookie')+(D?':'+Encode(D):'')+':';var M={};var N=','+J+"=";var O=null;var P='';var Q=0;var R=-1;var S=1;var T=document.cookie.split(';');for (var i=0;i<T.length;i++){var c=T[i];for (var j=0;j<c.length;j++){if (c.charAt(j)!=' ') break;};c=c.substring(j);if (c.indexOf(L)===0){var U=c.indexOf('=');Q=parseInt(c.substring(L.length,U),10);S=(Q>S?Q:S);O=L+Q;var W=c.indexOf(N);if (W>=0){var X=c.substring(U+1,W);var Y=c.substring(W+N.length);Y=Y.substring(Y.indexOf(','));Y=(!X&&Y==','?'':Y);P=X+Y;R=Q;}else{P=c.substring(U+1);};M[O]=P;}};if (!F) trivLogMsg('saveVariable for '+A+' to ['+B+']',2);if (G){G.setVariable(A,B,C);if ((!C||E)&&!document.TitleMgr&&!window.jTitleManager) return;};var Z=(R==-1&&C>=0);var a=(R!=-1&&C>=0);var b=(R!=-1&&C<0);var d=(b?'':N+K+',');if (Z||a||b){for (var e=0;e<=S+1;e++){if (e==R) continue;if (e==0&&Z) continue;O=L+(e==0?R:e);P=(M[O]==undefined?'':M[O]);if (P.length+d.length<4000){if (P&&!b) P=P.substring(0,P.length-1);document.cookie=O+'='+P+d+H;break;}else if (e==0){document.cookie=O+'='+P+H;}else if (d.length>=4000){if (!F) trivLogMsg('saveVariable failed for '+A+': length of value is greater than or equal to 4000 ['+d.length+']',2);break;}}}};function readVariable(A,B,C,D,E){convertCookies(D,E);if (typeof (Storage)!=="undefined"&&window.localStorage){var F=getTitleMgrHandle();if (F==null||F.findVariable(A)<0){var G="Lectora"+(D?(":"+Encode(D)):"");var H;if (C) H=localStorage.getItem(G);else H=sessionStorage.getItem(G);if (H){var I=H.split(',');for (var i=0;i<I.length;i++){if (I[i].length==0) continue;var J=I[i].split('=');if (J.length==2){var n=unescapeDelim(J[0]);var v=unescapeDelim(J[1]);if (n==A){B=v;break;}}}};if (F) F.setVariable(A,B,C);};if (F){var K=String(F.getVariable(A,B,C));B=K;}}else B=readVariableFromCookie(A,B,C,D,E);if (!E) trivLogMsg('readVariable for '+A+' = ['+B+']',1);return B;};function readVariableFromCookie(A,B,C,D,E){var F=getTitleMgrHandle();if (F==null||F.findVariable(A)<0){var G=(C?'~LectoraPermCookie':'~LectoraTempCookie')+(D?':'+Encode(D):'')+':';var H=','+Encode(A)+"=";var i;var I=document.cookie.split(';');for (i=0;i<I.length;i++){var c=I[i];for (var j=0;j<c.length;j++){if (c.charAt(j)!=' ') break;};c=c.substring(j);if (c.indexOf(G)==0){var J=c.indexOf(H);if (J>=0){var K=c.substring(J+H.length);K=K.substring(0,K.indexOf(','));var L=Decode(K);if (F) F.setVariable(A,L,C);if (!E) trivLogMsg('readVariable for '+A+' = ['+L+']',1);return L;}}}};if (F){var M=String(F.getVariable(A,B,C));B=M;};if (!E) trivLogMsg('readVariable for '+A+' = ['+B+']',1);return B;};function escapeDelim(s){s=s.toString();return s.replace(/%/g,"%25").replace(/\=/g,"%3D").replace(/,/g,"%2C");};function unescapeDelim(s){s=s.toString();return s.replace(/%2C/g,",").replace(/%3D/g,"=").replace(/%25/g,"%");};function convertCookies(A,B){if (!A) return;var C=['LectoraPermCookie','LectoraTempCookie'];var D=new RegExp('^(Lectora.*Cookie'+(A?'_'+A:'')+')([1-9])=(.*)');var E=document.cookie.split(';');var F={};var c=null;var i=0;var G=new Date();G.setTime(G.getTime()+(30*24*60*60*1000));var H="; expires="+G.toGMTString();G.setTime(0);var I="; expires="+G.toGMTString();for (i=0;i<E.length;i++){c=E[i];for (var j=0;j<c.length;j++){if (c.charAt(j)!=' ') break;};c=c.substring(j);var J=D.exec(c);if (J!=null&&J.length==4){var K=J[1];var L=parseInt(J[2]);var M=J[3];if (!F[K]) F[K]=[];F[K][L]=M;if (window.console&&console.log) console.log('convertCookies: found old cookie ['+(K+L)+'] length=['+M.length+']');document.cookie=K+L+'='+I+'; path=/';}};for (var N in F){if (typeof N=='function') continue;var O='';for (i=1;i<F[N].length;i++){if (typeof F[N][i]!='string') continue;O+=F[N][i];};var P=(N.indexOf('LectoraPerm')==0);var Q=1;var R=',';var S=O.split('|');if (window.console&&console.log) console.log('convertCookies: process old cookie ['+N+'] parts(max)=['+(F[N].length-1)+'] length=['+O.length+'] vars=['+S.length+']');if (typeof (Storage)!=="undefined"&&window.localStorage){var T="Lectora:"+Encode(A);var U="";for (i=0;i<S.length;i++){var W=S[i];var X=W.split('=');if (X.length==2){var Y=escapeDelim(X[0]);var Z=escapeDelim(UniUnescape(X[1]));var a=Y+'='+Z+',';U+=a;}};if (N.indexOf(C[0])==0) localStorage.setItem(T,U);else if (N.indexOf(C[1])==0) sessionStorage.setItem(T,U);if (window.console&&console.log) console.log('convertCookies: "'+N+'" to Web Storage: saving ['+T+']');if (!B) trivLogMsg('convertCookies "'+N+'" to Web Storage '+T+' with length '+U.length);}else{for (i=0;i<S.length;i++){var W=S[i];var X=W.split('=');if (X.length==2){var Y=Encode(X[0]);var Z=Encode(UniUnescape(X[1]));var a=Y+'='+Z+',';if (a.length>=4000){if (window.console&&console.log) console.log('convertCookies: VAR too large to process ['+Y+'] length=['+a.length+'] ++++++++++++++++++++++++++++++');if (!B) trivLogMsg('convertCookies found VAR ['+Y+'] too large to process - length is '+a.length);continue;};if (R.length+a.length<4000){R+=a;continue;};var h=N.replace('_',':');document.cookie='~'+h+':'+Q+'='+R+(P?H:'')+'; path=/';if (window.console&&console.log) console.log('convertCookies: saving ['+('~'+N+Q)+'] length=['+R.length+']');if (!B) trivLogMsg('convertCookies saving cookie '+('~'+N+Q)+' with length '+R.length);Q++;R=','+a;}};if (R.length>1){document.cookie='~'+N+Q+'='+R+(P?H:'')+'; path=/';if (window.console&&console.log) console.log('convertCookies: saving ['+('~'+N+Q)+'] length=['+R.length+']');if (!B) trivLogMsg('convertCookies saving cookie '+('~'+N+Q)+' with length '+R.length);}}}};function cleanupTitle(A){if (window.name.indexOf('Trivantis_')==-1){var c=null;var m=null;var B="; path=/";var C=new Date();C.setTime(C.getTime()+(-1*24*60*60*1000));B+="; expires="+C.toGMTString();var D='~LectoraTempCookie'+(A?':'+Encode(A):'');var E=new RegExp('^('+D+'[^=]*)');var F=document.cookie.split(';');for (var i=0;i<F.length;i++){c=F[i];for (var j=0;j<c.length;j++){if (c.charAt(j)!=' ') break;};c=c.substring(j);m=E.exec(c);if (m&&m.length==2) document.cookie=m[1]+'='+B;};if (typeof (Storage)!=="undefined"&&window.sessionStorage){var G="Lectora"+(A?(":"+Encode(A)):"");try{window.sessionStorage.removeItem(G);} catch (e){}};if (A!=A.toLowerCase()) cleanupTitle(A.toLowerCase());return 1;};return 0;};function Variable(A,B,C,D,E,F,G,H){var I=this;I.origAICC=f;I.bSCORM=f;I.of=C;I.f=C;I.eTS=n;I.tV=n;I.aiccframe=E;I.aiccgroup=n;I.aicccore=f;I.exp=F;if (typeof B=='function'){I.randomDefaultFunc=B;B=I.randomDefaultFunc();};I.defVal=(B===n||typeof B=='undefined'?n:B.toString());I.cm=0;I.title=G;I.lastUT=n;if (!H){this.bHidden=f;}else{this.bHidden=H;};if (D){I.cm=-1*D;if (A=='CM_Course_ID') I.name='TrivantisCourse';else if (A=='CM_Course_Name') I.name='TrivantisCourseName';else if (A=='CM_Student_ID') I.name='TrivantisLogin';else if (A=='CM_Student_Name') I.name='TrivantisLoginName';else{I.name=A;I.cm=D;}}else if (E){var J=A.indexOf('AICC_');if (J===0){I.origAICC=t;I.name=A.substring(5);var K=n;if (E=='scorm'||E=='tincan'){I.bSCORM=t;I.aiccgroup='cmi';I.name=I.name.toLowerCase();K=I.name.substring(0,5);if (K=='core_'){I.name=I.name.substring(5);};if (I.name=='lesson'){I.name='cmi.suspend_data';}else if (I.name=='vendor'){I.name='cmi.launch_data';}else if (I.name=='time'){I.name='cmi.core.total_time';}else if (I.name=='score'){I.name='cmi.core.score.raw';}else if (I.name=='student_language'){I.name='cmi.student_preference.language';}else{I.name='cmi.core.'+I.name;}}else if (E=='scorm2004'){I.bSCORM=t;I.aiccgroup='cmi';I.name=I.name.toLowerCase();K=I.name.substring(0,5);if (K=='core_'){I.name=I.name.substring(5);};if (I.name=='lesson'){I.name='cmi.suspend_data';}else if (I.name=='vendor'){I.name='cmi.launch_data';}else if (I.name=='time'){I.name='cmi.total_time';}else if (I.name=='score'){I.name='cmi.score.raw';}else if (I.name=='course_id'){I.name='cmi.evaluation.course_id';}else if (I.name=='lesson_id'){I.name='cmi.core.lesson_id';}else if (I.name=='student_id'){I.name='cmi.learner_id';}else if (I.name=='student_name'){I.name='cmi.learner_name';}else if (I.name=='lesson_location'){I.name='cmi.location';}else if (I.name=='lesson_status'){I.name='cmi.success_status';}else{I.name='cmi.'+I.name;}}else if (I.name=='Core_Lesson'){I.aiccgroup='[CORE_LESSON]';}else if (I.name=='Core_Vendor'){I.aiccgroup='[CORE_VENDOR]';}else if (I.name=='Course_ID'){I.aiccgroup='[EVALUATION]';}else{I.aiccgroup='[CORE]';I.aicccore=t;};if (!I.bSCORM){I.update();}}else{if (E=='scorm'||E=='scorm2004'||E=='tincan'){I.bSCORM=t;};if (A.indexOf('CMI_Core')===0){I.origAICC=t;I.aiccgroup='cmi';var L=(E=='scorm2004'?'cmi':'cmi.core');if (A=='CMI_Core_Entry'){I.name=L+'.entry';I.update();}else{I.name=L+'.exit';I.value=I.defVal;}}else if (A=='CMI_Completion_Status'){if (E=='scorm2004'){I.bSCORM=t;};I.origAICC=t;I.aiccgroup='cmi';I.name='cmi.completion_status';I.update();}else{I.name=A;}}}else{I.name=A;};if (I.f==4){I.uDT();}};var p=Variable.prototype;p.save=function(){var A=this;var B=n;if (A.cm){B=getTitleMgrHandle();if (B){B.setVariable(A.name,A.value,A.exp);}}else if (A.aiccframe){B=getTitleMgrHandle();if (A.bSCORM){var C=A.value;if (C=='~~~null~~~'){C=n;};if (A.name=='cmi.core.total_time'||A.name=='cmi.total_time'){if (A.aiccframe=='scorm'||A.aiccframe=='tincan'){LMSSetValue('cmi.core.session_time',C);if (B){B.setVariable('cmi.core.session_time',A.value,A.exp);}}else{LMSSetValue('cmi.session_time',C);if (B){B.setVariable('cmi.session_time',A.value,A.exp);}}}else{if (B){B.setVariable(A.name,A.value,A.exp);};if (A.aiccgroup){LMSSetValue(A.name,C);if (A.name=='cmi.score.raw'){var D=A.value/100;LMSSetValue('cmi.score.scaled',D);LMSCommit("");}else if (this.name=='cmi.core.score.raw'||this.name=='cmi.core.lesson_status'||this.name=='cmi.success_status'){LMSCommit("");}}else{var E=Encode(this.name)+"="+Encode(this.value)+';';var F=f;var G=String(GetSuspendData());if (G!=''){var H=G.split(';');for (var i=0;i<H.length;i++){var c=H[i];if (c!=''&&c.indexOf(Encode(this.name)+"=")!=0){E=E+c+';';}}};SetSuspendData(E);if (!A.bHidden) trivLogMsg('cmi.suspend_data (unescaped) is now set to ['+decodeURIComponent(E)+']');if(window.checkSuspendData){clearTimeout(checkSuspendData);};window.checkSuspendData=setTimeout(function(){var I=String(GetSuspendData());if (I.length<E.length){F=t;};window.checkSuspendData=n;if (F&&bDisplayErr){if (window.console&&console.log){console.log('WARNING: The LMS did not return what it was told to save.');console.log('SET to LMS:   ['+E+'] len='+E.length);console.log('GET from LMS: ['+I+'] len='+I.length);console.log('Error happens because the lengths of these values do not match.');};var J='Some of the persistent data was not able to be stored';trivLogMsg(J,2);alert(J);}},1000);}}}else{if (A.aicccore) putParam(A.aiccgroup,A.name+'='+A.value,A.aiccframe);else if (A.aiccgroup) putParam(A.aiccgroup,A.value,A.aiccframe);else{if (B) B.setVariable(A.name,A.value,A.exp);saveVariable(A.name,A.value,A.exp,A.title,A.aiccframe,A.bHidden);}}}else{if (A.f!==0&&A.tV>=0){if (A.f==4){saveVariable(A.name,"~~f=4~~"+A.tV+'#'+A.value,A.exp,A.title,A.aiccframe,A.bHidden);}else if (A.f==2){saveVariable(A.name,"~~f=2~~"+A.tV+'#'+A.value,A.exp,A.title,A.aiccframe,A.bHidden);}else if (A.f==1){saveVariable(A.name,"~~f=1~~"+A.tV+'#'+A.value,A.exp,A.title,A.aiccframe,A.bHidden);}};A.value=EncodeNull(A.value);saveVariable(A.name,A.value,A.exp,A.title,A.aiccframe,A.bHidden);}};p.set=function(A){var B=this;B.value=EncodeNull(A);B.f=0;B.eTS=n;B.tV=n;B.save();};p.isNumberString=function(A){return TRgEx.COMMA_SEP_NUM.test(A);};p.add=function(A){var B=this;this.update();var C=B.value.toString().replace(/,/g,'');var D=A.replace(/,/g,'');if (B.f>0&&!isNaN(A)){B.tV+=CalcTD(B.f,A);B.uDTV();}else if (B.value=="~~~null~~~"){B.f=0;if (A!==n&&A!==''){if (B.isNumberString(A)){B.value=(divide(Math.round(parseFloat(D)*1000000),1000000)).toString();}else{B.value=A;}}}else{B.f=0;if (A!==n&&A!==''){if (B.isNumberString(A)&&B.isNumberString(B.value.toString())){B.value=(divide(Math.round((parseFloat(C)+parseFloat(D))*1000000),1000000)).toString();}else if (A!="~~~null~~~"){B.value+=A;}}};B.save();};p.sub=function(A){var B=this;B.update();var C=B.value.toString().replace(/,/g,'');var D=A.replace(/,/g,'');if (B.f>0&&!isNaN(A)){B.tV-=CalcTD(B.f,A);B.uDTV();}else if (B.value=="~~~null~~~"){B.f=0;if (B.isNumberString(A)){B.value=(divide(Math.round(parseFloat("-"+D)*1000000),1000000)).toString();}}else{B.f=0;if (A!==n&&A!==""){if (B.isNumberString(A)&&B.isNumberString(B.value.toString())){B.value=(divide(Math.round((parseFloat(C)-parseFloat(D))*1000000),1000000)).toString();}else B.value=B.value.split(A).join('');}};B.save();};p.mult=function(A){var B=this;B.update();if (B.value!="~~~null~~~"){var C=B.value.toString().replace(/,/g,'');var D=A.replace(/,/g,'');if (!isNaN(D)&&!isNaN(C)&&!isNaN(parseFloat(D))&&!isNaN(parseFloat(C))){B.value=(divide(Math.round(parseFloat(C)*parseFloat(D)*1000000),1000000)).toString();};B.save();}};p.div=function(A){var B=this;B.update();if (B.value!="~~~null~~~"){var C=B.value.toString().replace(/,/g,'');var D=A.replace(/,/g,'');if (!isNaN(D)&&!isNaN(C)&&!isNaN(parseFloat(D))&&!isNaN(parseFloat(C))){if (parseFloat(D)!==0){B.value=(divide(Math.round(divide(C*1000000,D)),1000000)).toString();}};B.save();}};p.setByVar=function(A){var B=this;if (A.f>0){A.uDT();}else{A.update();};B.value=A.value;B.f=A.f;B.eTS=A.eTS;B.tV=A.tV;B.save();};p.addByVar=function VarAddVar(A){var B=this;if (A.f>0){A.uDT();if (B.f>0){B.tV+=A.tV;if (A.f==1){B.f=1;};B.uDTV();}else{B.add(A.value);}}else{A.update();B.add(A.value);}};p.subByVar=function(A){var B=this;if (A.f>0){A.uDT();if (B.f>0){B.tV-=A.tV;if (A.f==1){B.f=1;};B.uDTV();}else{B.sub(A.value);}}else{A.update();B.sub(A.value);}};p.isNullOrEmpty=function(){var A=this.value;return (A=="~~~null~~~"||(A==""&&A!=0));};p.contains=function(A){this.update();if (this.isNullOrEmpty()){return 0;};return (String(this.value)).indexOf(A)>=0;};p.equals=function(A){this.update();return (this.value==A);};p.lessThan=function(A){this.update();var B=this.value;if (B=="~~~null~~~"||(B==""&&B!=0)){if (A=="~~~null~~~"||A===""){return 0;}else{return 1;}};if (isNaN(B)||isNaN(A)){return B<A;}else{return parseFloat(B)<parseFloat(A);}};p.greaterThan=function(A){this.update();var B=this.value;if (B=="~~~null~~~"||(B==""&&B!=0)){if (A=="~~~null~~~"||A===""){return 1;}else{return 0;}};if (isNaN(B)||isNaN(A)){return B>A;}else{return parseFloat(B)>parseFloat(A);}};p.betweenInc=function(A,B){this.update();if (this.value=="~~~null~~~"||(this.value==""&&this.value!=0)){if (A=="~~~null~~~"||A=="") return 1;else if (B=="~~~null~~~"||B=="") return 1;else return 0;};if (isNaN(this.value)||isNaN(A)||isNaN(B)) return (this.value>=A&&this.value<=B);else return (parseFloat(this.value)>=parseFloat(A)&&parseFloat(this.value)<=parseFloat(B));};p.betweenExc=function(A,B){this.update();if (this.value=="~~~null~~~"||(this.value==""&&this.value!=0)){if (A=="~~~null~~~"||A=="") return 1;else if (B=="~~~null~~~"||B=="") return 1;else return 0;};if (isNaN(this.value)||isNaN(A)||isNaN(B)) return (this.value>A&&this.value<B);else return (parseFloat(this.value)>parseFloat(A)&&parseFloat(this.value)<parseFloat(B));};p.equalsNE=function(A){this.update();var B=this.value.toString().replace(/,/g,'');var C=A.replace(/,/g,'');if (!isNaN(parseFloat(B))&&!isNaN(parseFloat(C))){return (parseFloat(B)==parseFloat(C));}else{return 0;}};p.lessThanNE=function(A){this.update();var B=this.value;if (B=="~~~null~~~"||(B==""&&B!=0)){if (A=="~~~null~~~"||A===""){return 0;}else{return 1;}};var C=B.toString().replace(/,/g,'');var D=A.replace(/,/g,'');if (!isNaN(parseFloat(C))&&!isNaN(parseFloat(D))){return parseFloat(C)<parseFloat(D);}else{return 0;}};p.greaterThanNE=function(A){this.update();var B=this.value;if (B=="~~~null~~~"||(B==""&&B!=0)){if (A=="~~~null~~~"||A===""){return 1;}else{return 0;}};var C=B.toString().replace(/,/g,'');var D=A.replace(/,/g,'');if (!isNaN(parseFloat(C))&&!isNaN(parseFloat(D))){return parseFloat(C)>parseFloat(D);}else{return 0;}};p.betweenIncNE=function(A,B){this.update();if (this.value=="~~~null~~~"||(this.value==""&&this.value!=0)){if (A=="~~~null~~~"||A=="") return 1;else if (B=="~~~null~~~"||B=="") return 1;else return 0;};var C=this.value.toString().replace(/,/g,'');var D=A.replace(/,/g,'');var E=B.replace(/,/g,'');if (!isNaN(parseFloat(C))&&!isNaN(parseFloat(D))&&!isNaN(parseFloat(E))){return (parseFloat(C)>=parseFloat(D)&&parseFloat(C)<=parseFloat(E));}else{return 0;}};p.betweenExcNE=function(A,B){this.update();if (this.value=="~~~null~~~"||(this.value==""&&this.value!=0)){if (A=="~~~null~~~"||A=="") return 1;else if (B=="~~~null~~~"||B=="") return 1;else return 0;};var C=this.value.toString().replace(/,/g,'');var D=A.replace(/,/g,'');var E=B.replace(/,/g,'');if (!isNaN(parseFloat(C))&&!isNaN(parseFloat(D))&&!isNaN(parseFloat(E))){return (parseFloat(C)>parseFloat(D)&&parseFloat(C)<parseFloat(E));}else{return 0;}};p.uDT=function(){var A=this;var B=new Date();if (A.f==1){A.tV=B.getTime();A.value=FormatDS(B);}else if (A.f==2){A.tV=B.getTime();A.value=FormatTS(B);}else if (A.of==4){var C=0;if (A.eTS===n){var D=readVariable(A.name,"",A.exp,A.title);if (D){var E=parseInt(D,10);var F=D.indexOf(':');D=D.substring(F+1);var G=parseInt(D,10);F=D.indexOf(':');D=D.substring(F+1);var H=parseInt(D,10);C=(((E*60)+G)*60+H)*1000;};A.eTS=B.getTime()-C;};A.tV=B.getTime()-A.eTS;A.value=FormatETS(A.tV);};A.save();};p.uDTV=function(){var A=this;if (A.f==1){A.value=FormatDS(new Date(A.tV));}else if (A.f==2){A.value=FormatTS(new Date(A.tV));}else if (A.f==4){A.value=FormatETS(A.tV);};A.save();};p.update=function(){var A=this;var B=new Date().getTime();if (A.lastUT>=B-500){return;}else{A.lastUT=B;};var C=n;if (A.cm){if (A.cm<0){A.defVal=readCookie(A.name,A.defVal);A.cm*=-1;};C=getTitleMgrHandle();if (C){var D=String(C.getVariable(A.name,A.defVal,A.exp));A.value=D;}else A.value=A.defVal;}else if (A.aiccframe){C=getTitleMgrHandle();if (A.origAICC){if (A.bSCORM){if (A.name=='cmi.evaluation.course_id'){A.value=A.defVal;}else if (A.name=='cmi.core.lesson_id'){A.value=A.defVal;}else if (A.name!='cmi.core.exit'&&A.name!='cmi.exit'){var E=LMSGetValue(A.name);if (E==n) E=A.defVal;A.value=String(E);};if (C){C.setVariable(A.name,Encode(A.value),A.exp);if (A.name=='cmi.learner_id'){C.setVariable('cmi.core.student_id',A.value,A.exp);};if (A.name=='cmi.learner_name'){C.setVariable('cmi.core.student_name',A.value,A.exp);};if (A.name=='cmi.core.total_time'||A.name=='cmi.total_time'){A.value=UpdateSCORMTotalTime(A.value);}}}else if (A.name=='Core_Lesson'){A.value=getParam(A.aiccgroup);}else if (A.name=='Core_Vendor'){A.value=getParam(A.aiccgroup);}else if (A.name=='Course_ID'){A.value=getParam(A.name);}else{A.value=getParam(A.name);}}else{if (A.bSCORM){A.value=A.defVal;if (C&&C.findVariable(A.name)!=-1){var D=String(C.getVariable(A.name,A.defVal,A.exp));A.value=D;}else{var G=String(GetSuspendData());if (!A.bHidden) trivLogMsg('cmi.suspend_data (unescaped) is currently ['+decodeURIComponent(G)+']');if (G===''){if (C){C.setVariable(A.name,A.value,A.exp);}}else{var H=G.split(';');for (var i=0;i<H.length;i++){var c=H[i];if (c.indexOf('=')>=0){ce=c.split('=');if (A.name==Decode(ce[0])){A.value=Decode(ce[1]);};if (C){C.setVariable(Decode(ce[0]),Decode(ce[1]),A.exp);}}}}}}else{if (C){var D=String(C.getVariable(A.name,A.defVal,A.exp));A.value=D;}else{A.value=A.defVal;}}}}else if (A.f>0){A.uDT();}else{var J=readVariable(A.name,A.defVal,A.exp,A.title,A.bHidden);var K=J?J.substr(0,7):n;if (K=="~~f=1~~"){A.tV=parseInt(J.substr(7,J.length-7),10);A.f=1;A.uDTV();}else if (K=="~~f=2~~"){A.tV=parseInt(J.substr(7,J.length-7),10);A.f=2;A.uDTV();}else if (K=="~~f=4~~"){B=new Date();A.tV=parseInt(J.substr(7,J.length-7),10);A.eTS=B.getTime()-A.tV;A.f=4;A.uDTV();}else{A.value=J;}};A.value=EncodeNull(A.value);};p.getValue=function(){this.update();return this.value;};p.getValueForDisplay=function(){var A=this.getValue();return (A&&A.replace?A.replace("~~~null~~~",""):A);};p.isCorr=function(A){this.update();return (this.value.toString()==A)?t:f;};p.isCorrDD=function(A){this.update();var B=this.value.toString();B=B.toLowerCase();B=B.replace(/'/g,"\\'");var C=A.split(",");for (var i=0;i<C.length;i++) {var D=C[i].split("|");var E=false;for (var j=0;j<D.length;j++) {if (B.indexOf(D[j].toLowerCase())>=0) E=true;};if (!E) return false;};return true;};p.isCorrSub=function(A,B){this.update();var C=A.split(",");return (this.value.toString().indexOf(C[B?B:0])>=0)?t:f;};p.isAnsSub=function(A){this.update();var B=','+(A+1)+'-';var C=','+this.value;return (C.indexOf(B)>=0)?t:f;};p.isCorrFIB=function(A,B,C){this.update();var D;var E;var F=f;var G=A.split("~;~");if (!B) E=this.value.toString().toLowerCase();else E=this.value.toString();for (var i=0;i<G.length;i++){if (!B) D=G[i].toLowerCase();else D=G[i];if (C){if (E==D) return t;}else{if (E.indexOf(D)==-1) return f;else F=t;}};return F;};p.isCorrNE=function(A){this.update();var B=this.value+'',C=f;if (B!="~~~null~~~"){try{C=eval(A.replace(/##/g,B).replace(/,/g,''));} catch (e){}};return C;};p.reset=function(){this.set(this.randomDefaultFunc?this.randomDefaultFunc():this.defVal);};function saveTestScore(A,B,C,D){saveVariable(A,B,n,C,D);};var titleMgrHandle=n;var getFn=n;function getTitleMgrHandle(){if (titleMgrHandle===n){try{titleMgrHandle=getTitleMgr(window,0);} catch (error){titleMgrHandle=n;}};return titleMgrHandle;};function getTitleMgr(A,B){A=getTitleMgrWnd(A,B);if (A){if (A.jTitleManager) return A.jTitleManager;else if (A.document.titleMgr) return A.document.titleMgr;};return null;};function getTitleMgrWnd(A,B){if (!A) return null;if (A.jTitleManager) return A;else if (A.document.titleMgr) return A;else{var C;if (this.frameElement&&this.frameElement.id&&this.frameElement.id.indexOf('DLG_content')==0&&parent.parent) C=eval("parent.parent.titlemgrframe");else C=eval("parent.titlemgrframe");try{if (!C) C=A.parent.titlemgrframe;} catch (e){};if (C) return C;else{if (A.name.indexOf('Trivantis_Dlg_')==0) return getTitleMgrWnd(A.parent,B+1);else{if (A.name.indexOf('Trivantis_')==0) return getTitleMgrWnd(A.opener,B+1);else if (B<2) return getTitleMgrWnd(A.parent,B+1);}}};return null;};function readCookie(A,B){var C=A+"=";var D=document.cookie.split(';');for (var i=0;i<D.length;i++){var c=D[i];while (c.charAt(0)==' '){c=c.substring(1);};if (c.indexOf(C)===0){return c.substring(C.length);}};return B;};function afterProcessTest(A,B){};function UpdateSCORMTotalTime(A){var B=readVariable('TrivantisSCORMTimer',0);if (B===0){return A;};var C=new Date().getTime();var D=C-B;var E=parseInt(A,10);var F=A.indexOf(':');A=A.substring(F+1);var G=parseInt(A,10);F=A.indexOf(':');A=A.substring(F+1);var H=parseInt(A,10);F=A.indexOf('.');A=A.substring(F+1);var I=parseInt(A,10)*100;var J=(((E*60)+G)*60+H)*1000+I;return convertTotalMills(J+D);};function EncodeNull(A){return (A==n)?"~~~null~~~":((String(A)=="0")?0:((A=="")?"~~~null~~~":A));};function GetSuspendData(){var A=String(LMSGetValue('cmi.suspend_data'));if (A.length>2){if (A.indexOf(";~;")==0) A=A.substring(3);else A=NewEncode(A);};return A;};function SetSuspendData(A){LMSSetValue('cmi.suspend_data',";~;"+A);};function NewEncode(A){var B='';if (A!=''){var C=A.split(';');for (var i=0;i<C.length;i++){var c=C[i];if (c!=''){var D=c.split('=');if (D.length==2) B=B+Encode(D[0])+"="+Encode(UniUnescape(D[1]))+";";}}};return (B);};function trivScormQuit(A,B,C){var D=(window.name.indexOf('Trivantis_')==-1);if (C) saveVariable('TrivantisEPS','T');if (D){try{cleanupTitle(B);}finally{doQuit(A);}}else trivCloseWnd();}
