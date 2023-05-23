(function(){const e=document.createElement("link").relList;if(e&&e.supports&&e.supports("modulepreload"))return;for(const o of document.querySelectorAll('link[rel="modulepreload"]'))n(o);new MutationObserver(o=>{for(const r of o)if(r.type==="childList")for(const s of r.addedNodes)s.tagName==="LINK"&&s.rel==="modulepreload"&&n(s)}).observe(document,{childList:!0,subtree:!0});function t(o){const r={};return o.integrity&&(r.integrity=o.integrity),o.referrerPolicy&&(r.referrerPolicy=o.referrerPolicy),o.crossOrigin==="use-credentials"?r.credentials="include":o.crossOrigin==="anonymous"?r.credentials="omit":r.credentials="same-origin",r}function n(o){if(o.ep)return;o.ep=!0;const r=t(o);fetch(o.href,r)}})();window.Vaadin=window.Vaadin||{};window.Vaadin.featureFlags=window.Vaadin.featureFlags||{};window.Vaadin.featureFlags.exampleFeatureFlag=!1;window.Vaadin.featureFlags.collaborationEngineBackend=!1;const Ei="modulepreload",Ci=function(i,e){return new URL(i,e).href},vt={},we=function(e,t,n){if(!t||t.length===0)return e();const o=document.getElementsByTagName("link");return Promise.all(t.map(r=>{if(r=Ci(r,n),r in vt)return;vt[r]=!0;const s=r.endsWith(".css"),l=s?'[rel="stylesheet"]':"";if(!!n)for(let c=o.length-1;c>=0;c--){const u=o[c];if(u.href===r&&(!s||u.rel==="stylesheet"))return}else if(document.querySelector(`link[href="${r}"]${l}`))return;const d=document.createElement("link");if(d.rel=s?"stylesheet":Ei,s||(d.as="script",d.crossOrigin=""),d.href=r,document.head.appendChild(d),s)return new Promise((c,u)=>{d.addEventListener("load",c),d.addEventListener("error",()=>u(new Error(`Unable to preload CSS for ${r}`)))})})).then(()=>e())};function Se(i){return i=i||[],Array.isArray(i)?i:[i]}function A(i){return`[Vaadin.Router] ${i}`}function $i(i){if(typeof i!="object")return String(i);const e=Object.prototype.toString.call(i).match(/ (.*)\]$/)[1];return e==="Object"||e==="Array"?`${e} ${JSON.stringify(i)}`:e}const Ee="module",Ce="nomodule",tt=[Ee,Ce];function _t(i){if(!i.match(/.+\.[m]?js$/))throw new Error(A(`Unsupported type for bundle "${i}": .js or .mjs expected.`))}function Kt(i){if(!i||!k(i.path))throw new Error(A('Expected route config to be an object with a "path" string property, or an array of such objects'));const e=i.bundle,t=["component","redirect","bundle"];if(!F(i.action)&&!Array.isArray(i.children)&&!F(i.children)&&!$e(e)&&!t.some(n=>k(i[n])))throw new Error(A(`Expected route config "${i.path}" to include either "${t.join('", "')}" or "action" function but none found.`));if(e)if(k(e))_t(e);else if(tt.some(n=>n in e))tt.forEach(n=>n in e&&_t(e[n]));else throw new Error(A('Expected route bundle to include either "'+Ce+'" or "'+Ee+'" keys, or both'));i.redirect&&["bundle","component"].forEach(n=>{n in i&&console.warn(A(`Route config "${i.path}" has both "redirect" and "${n}" properties, and "redirect" will always override the latter. Did you mean to only use "${n}"?`))})}function yt(i){Se(i).forEach(e=>Kt(e))}function bt(i,e){let t=document.head.querySelector('script[src="'+i+'"][async]');return t||(t=document.createElement("script"),t.setAttribute("src",i),e===Ee?t.setAttribute("type",Ee):e===Ce&&t.setAttribute(Ce,""),t.async=!0),new Promise((n,o)=>{t.onreadystatechange=t.onload=r=>{t.__dynamicImportLoaded=!0,n(r)},t.onerror=r=>{t.parentNode&&t.parentNode.removeChild(t),o(r)},t.parentNode===null?document.head.appendChild(t):t.__dynamicImportLoaded&&n()})}function Ti(i){return k(i)?bt(i):Promise.race(tt.filter(e=>e in i).map(e=>bt(i[e],e)))}function ie(i,e){return!window.dispatchEvent(new CustomEvent(`vaadin-router-${i}`,{cancelable:i==="go",detail:e}))}function $e(i){return typeof i=="object"&&!!i}function F(i){return typeof i=="function"}function k(i){return typeof i=="string"}function Yt(i){const e=new Error(A(`Page not found (${i.pathname})`));return e.context=i,e.code=404,e}const G=new class{};function xi(i){const e=i.port,t=i.protocol,r=t==="http:"&&e==="80"||t==="https:"&&e==="443"?i.hostname:i.host;return`${t}//${r}`}function wt(i){if(i.defaultPrevented||i.button!==0||i.shiftKey||i.ctrlKey||i.altKey||i.metaKey)return;let e=i.target;const t=i.composedPath?i.composedPath():i.path||[];for(let l=0;l<t.length;l++){const a=t[l];if(a.nodeName&&a.nodeName.toLowerCase()==="a"){e=a;break}}for(;e&&e.nodeName.toLowerCase()!=="a";)e=e.parentNode;if(!e||e.nodeName.toLowerCase()!=="a"||e.target&&e.target.toLowerCase()!=="_self"||e.hasAttribute("download")||e.hasAttribute("router-ignore")||e.pathname===window.location.pathname&&e.hash!==""||(e.origin||xi(e))!==window.location.origin)return;const{pathname:o,search:r,hash:s}=e;ie("go",{pathname:o,search:r,hash:s})&&(i.preventDefault(),i&&i.type==="click"&&window.scrollTo(0,0))}const Ri={activate(){window.document.addEventListener("click",wt)},inactivate(){window.document.removeEventListener("click",wt)}},Ni=/Trident/.test(navigator.userAgent);Ni&&!F(window.PopStateEvent)&&(window.PopStateEvent=function(i,e){e=e||{};var t=document.createEvent("Event");return t.initEvent(i,Boolean(e.bubbles),Boolean(e.cancelable)),t.state=e.state||null,t},window.PopStateEvent.prototype=window.Event.prototype);function St(i){if(i.state==="vaadin-router-ignore")return;const{pathname:e,search:t,hash:n}=window.location;ie("go",{pathname:e,search:t,hash:n})}const ki={activate(){window.addEventListener("popstate",St)},inactivate(){window.removeEventListener("popstate",St)}};var Q=ti,Ai=at,Li=Di,Ii=Qt,Pi=ei,Jt="/",Xt="./",Oi=new RegExp(["(\\\\.)","(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?"].join("|"),"g");function at(i,e){for(var t=[],n=0,o=0,r="",s=e&&e.delimiter||Jt,l=e&&e.delimiters||Xt,a=!1,d;(d=Oi.exec(i))!==null;){var c=d[0],u=d[1],h=d.index;if(r+=i.slice(o,h),o=h+c.length,u){r+=u[1],a=!0;continue}var p="",W=i[o],Ae=d[2],j=d[3],yi=d[4],he=d[5];if(!a&&r.length){var Le=r.length-1;l.indexOf(r[Le])>-1&&(p=r[Le],r=r.slice(0,Le))}r&&(t.push(r),r="",a=!1);var bi=p!==""&&W!==void 0&&W!==p,wi=he==="+"||he==="*",Si=he==="?"||he==="*",mt=p||s,gt=j||yi;t.push({name:Ae||n++,prefix:p,delimiter:mt,optional:Si,repeat:wi,partial:bi,pattern:gt?Vi(gt):"[^"+D(mt)+"]+?"})}return(r||o<i.length)&&t.push(r+i.substr(o)),t}function Di(i,e){return Qt(at(i,e))}function Qt(i){for(var e=new Array(i.length),t=0;t<i.length;t++)typeof i[t]=="object"&&(e[t]=new RegExp("^(?:"+i[t].pattern+")$"));return function(n,o){for(var r="",s=o&&o.encode||encodeURIComponent,l=0;l<i.length;l++){var a=i[l];if(typeof a=="string"){r+=a;continue}var d=n?n[a.name]:void 0,c;if(Array.isArray(d)){if(!a.repeat)throw new TypeError('Expected "'+a.name+'" to not repeat, but got array');if(d.length===0){if(a.optional)continue;throw new TypeError('Expected "'+a.name+'" to not be empty')}for(var u=0;u<d.length;u++){if(c=s(d[u],a),!e[l].test(c))throw new TypeError('Expected all "'+a.name+'" to match "'+a.pattern+'"');r+=(u===0?a.prefix:a.delimiter)+c}continue}if(typeof d=="string"||typeof d=="number"||typeof d=="boolean"){if(c=s(String(d),a),!e[l].test(c))throw new TypeError('Expected "'+a.name+'" to match "'+a.pattern+'", but got "'+c+'"');r+=a.prefix+c;continue}if(a.optional){a.partial&&(r+=a.prefix);continue}throw new TypeError('Expected "'+a.name+'" to be '+(a.repeat?"an array":"a string"))}return r}}function D(i){return i.replace(/([.+*?=^!:${}()[\]|/\\])/g,"\\$1")}function Vi(i){return i.replace(/([=!:$/()])/g,"\\$1")}function Zt(i){return i&&i.sensitive?"":"i"}function Mi(i,e){if(!e)return i;var t=i.source.match(/\((?!\?)/g);if(t)for(var n=0;n<t.length;n++)e.push({name:n,prefix:null,delimiter:null,optional:!1,repeat:!1,partial:!1,pattern:null});return i}function Ui(i,e,t){for(var n=[],o=0;o<i.length;o++)n.push(ti(i[o],e,t).source);return new RegExp("(?:"+n.join("|")+")",Zt(t))}function Fi(i,e,t){return ei(at(i,t),e,t)}function ei(i,e,t){t=t||{};for(var n=t.strict,o=t.start!==!1,r=t.end!==!1,s=D(t.delimiter||Jt),l=t.delimiters||Xt,a=[].concat(t.endsWith||[]).map(D).concat("$").join("|"),d=o?"^":"",c=i.length===0,u=0;u<i.length;u++){var h=i[u];if(typeof h=="string")d+=D(h),c=u===i.length-1&&l.indexOf(h[h.length-1])>-1;else{var p=h.repeat?"(?:"+h.pattern+")(?:"+D(h.delimiter)+"(?:"+h.pattern+"))*":h.pattern;e&&e.push(h),h.optional?h.partial?d+=D(h.prefix)+"("+p+")?":d+="(?:"+D(h.prefix)+"("+p+"))?":d+=D(h.prefix)+"("+p+")"}}return r?(n||(d+="(?:"+s+")?"),d+=a==="$"?"$":"(?="+a+")"):(n||(d+="(?:"+s+"(?="+a+"))?"),c||(d+="(?="+s+"|"+a+")")),new RegExp(d,Zt(t))}function ti(i,e,t){return i instanceof RegExp?Mi(i,e):Array.isArray(i)?Ui(i,e,t):Fi(i,e,t)}Q.parse=Ai;Q.compile=Li;Q.tokensToFunction=Ii;Q.tokensToRegExp=Pi;const{hasOwnProperty:Bi}=Object.prototype,it=new Map;it.set("|false",{keys:[],pattern:/(?:)/});function Et(i){try{return decodeURIComponent(i)}catch{return i}}function zi(i,e,t,n,o){t=!!t;const r=`${i}|${t}`;let s=it.get(r);if(!s){const d=[];s={keys:d,pattern:Q(i,d,{end:t,strict:i===""})},it.set(r,s)}const l=s.pattern.exec(e);if(!l)return null;const a=Object.assign({},o);for(let d=1;d<l.length;d++){const c=s.keys[d-1],u=c.name,h=l[d];(h!==void 0||!Bi.call(a,u))&&(c.repeat?a[u]=h?h.split(c.delimiter).map(Et):[]:a[u]=h&&Et(h))}return{path:l[0],keys:(n||[]).concat(s.keys),params:a}}function ii(i,e,t,n,o){let r,s,l=0,a=i.path||"";return a.charAt(0)==="/"&&(t&&(a=a.substr(1)),t=!0),{next(d){if(i===d)return{done:!0};const c=i.__children=i.__children||i.children;if(!r&&(r=zi(a,e,!c,n,o),r))return{done:!1,value:{route:i,keys:r.keys,params:r.params,path:r.path}};if(r&&c)for(;l<c.length;){if(!s){const h=c[l];h.parent=i;let p=r.path.length;p>0&&e.charAt(p)==="/"&&(p+=1),s=ii(h,e.substr(p),t,r.keys,r.params)}const u=s.next(d);if(!u.done)return{done:!1,value:u.value};s=null,l++}return{done:!0}}}}function Hi(i){if(F(i.route.action))return i.route.action(i)}function Wi(i,e){let t=e;for(;t;)if(t=t.parent,t===i)return!0;return!1}function ji(i){let e=`Path '${i.pathname}' is not properly resolved due to an error.`;const t=(i.route||{}).path;return t&&(e+=` Resolution had failed on route: '${t}'`),e}function Gi(i,e){const{route:t,path:n}=e;if(t&&!t.__synthetic){const o={path:n,route:t};if(!i.chain)i.chain=[];else if(t.parent){let r=i.chain.length;for(;r--&&i.chain[r].route&&i.chain[r].route!==t.parent;)i.chain.pop()}i.chain.push(o)}}class oe{constructor(e,t={}){if(Object(e)!==e)throw new TypeError("Invalid routes");this.baseUrl=t.baseUrl||"",this.errorHandler=t.errorHandler,this.resolveRoute=t.resolveRoute||Hi,this.context=Object.assign({resolver:this},t.context),this.root=Array.isArray(e)?{path:"",__children:e,parent:null,__synthetic:!0}:e,this.root.parent=null}getRoutes(){return[...this.root.__children]}setRoutes(e){yt(e);const t=[...Se(e)];this.root.__children=t}addRoutes(e){return yt(e),this.root.__children.push(...Se(e)),this.getRoutes()}removeRoutes(){this.setRoutes([])}resolve(e){const t=Object.assign({},this.context,k(e)?{pathname:e}:e),n=ii(this.root,this.__normalizePathname(t.pathname),this.baseUrl),o=this.resolveRoute;let r=null,s=null,l=t;function a(d,c=r.value.route,u){const h=u===null&&r.value.route;return r=s||n.next(h),s=null,!d&&(r.done||!Wi(c,r.value.route))?(s=r,Promise.resolve(G)):r.done?Promise.reject(Yt(t)):(l=Object.assign(l?{chain:l.chain?l.chain.slice(0):[]}:{},t,r.value),Gi(l,r.value),Promise.resolve(o(l)).then(p=>p!=null&&p!==G?(l.result=p.result||p,l):a(d,c,p)))}return t.next=a,Promise.resolve().then(()=>a(!0,this.root)).catch(d=>{const c=ji(l);if(d?console.warn(c):d=new Error(c),d.context=d.context||l,d instanceof DOMException||(d.code=d.code||500),this.errorHandler)return l.result=this.errorHandler(d),l;throw d})}static __createUrl(e,t){return new URL(e,t)}get __effectiveBaseUrl(){return this.baseUrl?this.constructor.__createUrl(this.baseUrl,document.baseURI||document.URL).href.replace(/[^\/]*$/,""):""}__normalizePathname(e){if(!this.baseUrl)return e;const t=this.__effectiveBaseUrl,n=this.constructor.__createUrl(e,t).href;if(n.slice(0,t.length)===t)return n.slice(t.length)}}oe.pathToRegexp=Q;const{pathToRegexp:Ct}=oe,$t=new Map;function ni(i,e,t){const n=e.name||e.component;if(n&&(i.has(n)?i.get(n).push(e):i.set(n,[e])),Array.isArray(t))for(let o=0;o<t.length;o++){const r=t[o];r.parent=e,ni(i,r,r.__children||r.children)}}function Tt(i,e){const t=i.get(e);if(t&&t.length>1)throw new Error(`Duplicate route with name "${e}". Try seting unique 'name' route properties.`);return t&&t[0]}function xt(i){let e=i.path;return e=Array.isArray(e)?e[0]:e,e!==void 0?e:""}function qi(i,e={}){if(!(i instanceof oe))throw new TypeError("An instance of Resolver is expected");const t=new Map;return(n,o)=>{let r=Tt(t,n);if(!r&&(t.clear(),ni(t,i.root,i.root.__children),r=Tt(t,n),!r))throw new Error(`Route "${n}" not found`);let s=$t.get(r.fullPath);if(!s){let a=xt(r),d=r.parent;for(;d;){const p=xt(d);p&&(a=p.replace(/\/$/,"")+"/"+a.replace(/^\//,"")),d=d.parent}const c=Ct.parse(a),u=Ct.tokensToFunction(c),h=Object.create(null);for(let p=0;p<c.length;p++)k(c[p])||(h[c[p].name]=!0);s={toPath:u,keys:h},$t.set(a,s),r.fullPath=a}let l=s.toPath(o,e)||"/";if(e.stringifyQueryParams&&o){const a={},d=Object.keys(o);for(let u=0;u<d.length;u++){const h=d[u];s.keys[h]||(a[h]=o[h])}const c=e.stringifyQueryParams(a);c&&(l+=c.charAt(0)==="?"?c:`?${c}`)}return l}}let Rt=[];function Ki(i){Rt.forEach(e=>e.inactivate()),i.forEach(e=>e.activate()),Rt=i}const Yi=i=>{const e=getComputedStyle(i).getPropertyValue("animation-name");return e&&e!=="none"},Ji=(i,e)=>{const t=()=>{i.removeEventListener("animationend",t),e()};i.addEventListener("animationend",t)};function Nt(i,e){return i.classList.add(e),new Promise(t=>{if(Yi(i)){const n=i.getBoundingClientRect(),o=`height: ${n.bottom-n.top}px; width: ${n.right-n.left}px`;i.setAttribute("style",`position: absolute; ${o}`),Ji(i,()=>{i.classList.remove(e),i.removeAttribute("style"),t()})}else i.classList.remove(e),t()})}const Xi=256;function Ie(i){return i!=null}function Qi(i){const e=Object.assign({},i);return delete e.next,e}function R({pathname:i="",search:e="",hash:t="",chain:n=[],params:o={},redirectFrom:r,resolver:s},l){const a=n.map(d=>d.route);return{baseUrl:s&&s.baseUrl||"",pathname:i,search:e,hash:t,routes:a,route:l||a.length&&a[a.length-1]||null,params:o,redirectFrom:r,getUrl:(d={})=>me(V.pathToRegexp.compile(oi(a))(Object.assign({},o,d)),s)}}function kt(i,e){const t=Object.assign({},i.params);return{redirect:{pathname:e,from:i.pathname,params:t}}}function Zi(i,e){e.location=R(i);const t=i.chain.map(n=>n.route).indexOf(i.route);return i.chain[t].element=e,e}function fe(i,e,t){if(F(i))return i.apply(t,e)}function At(i,e,t){return n=>{if(n&&(n.cancel||n.redirect))return n;if(t)return fe(t[i],e,t)}}function en(i,e){if(!Array.isArray(i)&&!$e(i))throw new Error(A(`Incorrect "children" value for the route ${e.path}: expected array or object, but got ${i}`));e.__children=[];const t=Se(i);for(let n=0;n<t.length;n++)Kt(t[n]),e.__children.push(t[n])}function ue(i){if(i&&i.length){const e=i[0].parentNode;for(let t=0;t<i.length;t++)e.removeChild(i[t])}}function me(i,e){const t=e.__effectiveBaseUrl;return t?e.constructor.__createUrl(i.replace(/^\//,""),t).pathname:i}function oi(i){return i.map(e=>e.path).reduce((e,t)=>t.length?e.replace(/\/$/,"")+"/"+t.replace(/^\//,""):e,"")}class V extends oe{constructor(e,t){const n=document.head.querySelector("base"),o=n&&n.getAttribute("href");super([],Object.assign({baseUrl:o&&oe.__createUrl(o,document.URL).pathname.replace(/[^\/]*$/,"")},t)),this.resolveRoute=s=>this.__resolveRoute(s);const r=V.NavigationTrigger;V.setTriggers.apply(V,Object.keys(r).map(s=>r[s])),this.baseUrl,this.ready,this.ready=Promise.resolve(e),this.location,this.location=R({resolver:this}),this.__lastStartedRenderId=0,this.__navigationEventHandler=this.__onNavigationEvent.bind(this),this.setOutlet(e),this.subscribe(),this.__createdByRouter=new WeakMap,this.__addedByRouter=new WeakMap}__resolveRoute(e){const t=e.route;let n=Promise.resolve();F(t.children)&&(n=n.then(()=>t.children(Qi(e))).then(r=>{!Ie(r)&&!F(t.children)&&(r=t.children),en(r,t)}));const o={redirect:r=>kt(e,r),component:r=>{const s=document.createElement(r);return this.__createdByRouter.set(s,!0),s}};return n.then(()=>{if(this.__isLatestRender(e))return fe(t.action,[e,o],t)}).then(r=>{if(Ie(r)&&(r instanceof HTMLElement||r.redirect||r===G))return r;if(k(t.redirect))return o.redirect(t.redirect);if(t.bundle)return Ti(t.bundle).then(()=>{},()=>{throw new Error(A(`Bundle not found: ${t.bundle}. Check if the file name is correct`))})}).then(r=>{if(Ie(r))return r;if(k(t.component))return o.component(t.component)})}setOutlet(e){e&&this.__ensureOutlet(e),this.__outlet=e}getOutlet(){return this.__outlet}setRoutes(e,t=!1){return this.__previousContext=void 0,this.__urlForName=void 0,super.setRoutes(e),t||this.__onNavigationEvent(),this.ready}render(e,t){const n=++this.__lastStartedRenderId,o=Object.assign({search:"",hash:""},k(e)?{pathname:e}:e,{__renderId:n});return this.ready=this.resolve(o).then(r=>this.__fullyResolveChain(r)).then(r=>{if(this.__isLatestRender(r)){const s=this.__previousContext;if(r===s)return this.__updateBrowserHistory(s,!0),this.location;if(this.location=R(r),t&&this.__updateBrowserHistory(r,n===1),ie("location-changed",{router:this,location:this.location}),r.__skipAttach)return this.__copyUnchangedElements(r,s),this.__previousContext=r,this.location;this.__addAppearingContent(r,s);const l=this.__animateIfNeeded(r);return this.__runOnAfterEnterCallbacks(r),this.__runOnAfterLeaveCallbacks(r,s),l.then(()=>{if(this.__isLatestRender(r))return this.__removeDisappearingContent(),this.__previousContext=r,this.location})}}).catch(r=>{if(n===this.__lastStartedRenderId)throw t&&this.__updateBrowserHistory(o),ue(this.__outlet&&this.__outlet.children),this.location=R(Object.assign(o,{resolver:this})),ie("error",Object.assign({router:this,error:r},o)),r}),this.ready}__fullyResolveChain(e,t=e){return this.__findComponentContextAfterAllRedirects(t).then(n=>{const r=n!==t?n:e,l=me(oi(n.chain),n.resolver)===n.pathname,a=(d,c=d.route,u)=>d.next(void 0,c,u).then(h=>h===null||h===G?l?d:c.parent!==null?a(d,c.parent,h):h:h);return a(n).then(d=>{if(d===null||d===G)throw Yt(r);return d&&d!==G&&d!==n?this.__fullyResolveChain(r,d):this.__amendWithOnBeforeCallbacks(n)})})}__findComponentContextAfterAllRedirects(e){const t=e.result;return t instanceof HTMLElement?(Zi(e,t),Promise.resolve(e)):t.redirect?this.__redirect(t.redirect,e.__redirectCount,e.__renderId).then(n=>this.__findComponentContextAfterAllRedirects(n)):t instanceof Error?Promise.reject(t):Promise.reject(new Error(A(`Invalid route resolution result for path "${e.pathname}". Expected redirect object or HTML element, but got: "${$i(t)}". Double check the action return value for the route.`)))}__amendWithOnBeforeCallbacks(e){return this.__runOnBeforeCallbacks(e).then(t=>t===this.__previousContext||t===e?t:this.__fullyResolveChain(t))}__runOnBeforeCallbacks(e){const t=this.__previousContext||{},n=t.chain||[],o=e.chain;let r=Promise.resolve();const s=()=>({cancel:!0}),l=a=>kt(e,a);if(e.__divergedChainIndex=0,e.__skipAttach=!1,n.length){for(let a=0;a<Math.min(n.length,o.length)&&!(n[a].route!==o[a].route||n[a].path!==o[a].path&&n[a].element!==o[a].element||!this.__isReusableElement(n[a].element,o[a].element));a=++e.__divergedChainIndex);if(e.__skipAttach=o.length===n.length&&e.__divergedChainIndex==o.length&&this.__isReusableElement(e.result,t.result),e.__skipAttach){for(let a=o.length-1;a>=0;a--)r=this.__runOnBeforeLeaveCallbacks(r,e,{prevent:s},n[a]);for(let a=0;a<o.length;a++)r=this.__runOnBeforeEnterCallbacks(r,e,{prevent:s,redirect:l},o[a]),n[a].element.location=R(e,n[a].route)}else for(let a=n.length-1;a>=e.__divergedChainIndex;a--)r=this.__runOnBeforeLeaveCallbacks(r,e,{prevent:s},n[a])}if(!e.__skipAttach)for(let a=0;a<o.length;a++)a<e.__divergedChainIndex?a<n.length&&n[a].element&&(n[a].element.location=R(e,n[a].route)):(r=this.__runOnBeforeEnterCallbacks(r,e,{prevent:s,redirect:l},o[a]),o[a].element&&(o[a].element.location=R(e,o[a].route)));return r.then(a=>{if(a){if(a.cancel)return this.__previousContext.__renderId=e.__renderId,this.__previousContext;if(a.redirect)return this.__redirect(a.redirect,e.__redirectCount,e.__renderId)}return e})}__runOnBeforeLeaveCallbacks(e,t,n,o){const r=R(t);return e.then(s=>{if(this.__isLatestRender(t))return At("onBeforeLeave",[r,n,this],o.element)(s)}).then(s=>{if(!(s||{}).redirect)return s})}__runOnBeforeEnterCallbacks(e,t,n,o){const r=R(t,o.route);return e.then(s=>{if(this.__isLatestRender(t))return At("onBeforeEnter",[r,n,this],o.element)(s)})}__isReusableElement(e,t){return e&&t?this.__createdByRouter.get(e)&&this.__createdByRouter.get(t)?e.localName===t.localName:e===t:!1}__isLatestRender(e){return e.__renderId===this.__lastStartedRenderId}__redirect(e,t,n){if(t>Xi)throw new Error(A(`Too many redirects when rendering ${e.from}`));return this.resolve({pathname:this.urlForPath(e.pathname,e.params),redirectFrom:e.from,__redirectCount:(t||0)+1,__renderId:n})}__ensureOutlet(e=this.__outlet){if(!(e instanceof Node))throw new TypeError(A(`Expected router outlet to be a valid DOM Node (but got ${e})`))}__updateBrowserHistory({pathname:e,search:t="",hash:n=""},o){if(window.location.pathname!==e||window.location.search!==t||window.location.hash!==n){const r=o?"replaceState":"pushState";window.history[r](null,document.title,e+t+n),window.dispatchEvent(new PopStateEvent("popstate",{state:"vaadin-router-ignore"}))}}__copyUnchangedElements(e,t){let n=this.__outlet;for(let o=0;o<e.__divergedChainIndex;o++){const r=t&&t.chain[o].element;if(r)if(r.parentNode===n)e.chain[o].element=r,n=r;else break}return n}__addAppearingContent(e,t){this.__ensureOutlet(),this.__removeAppearingContent();const n=this.__copyUnchangedElements(e,t);this.__appearingContent=[],this.__disappearingContent=Array.from(n.children).filter(r=>this.__addedByRouter.get(r)&&r!==e.result);let o=n;for(let r=e.__divergedChainIndex;r<e.chain.length;r++){const s=e.chain[r].element;s&&(o.appendChild(s),this.__addedByRouter.set(s,!0),o===n&&this.__appearingContent.push(s),o=s)}}__removeDisappearingContent(){this.__disappearingContent&&ue(this.__disappearingContent),this.__disappearingContent=null,this.__appearingContent=null}__removeAppearingContent(){this.__disappearingContent&&this.__appearingContent&&(ue(this.__appearingContent),this.__disappearingContent=null,this.__appearingContent=null)}__runOnAfterLeaveCallbacks(e,t){if(t)for(let n=t.chain.length-1;n>=e.__divergedChainIndex&&this.__isLatestRender(e);n--){const o=t.chain[n].element;if(o)try{const r=R(e);fe(o.onAfterLeave,[r,{},t.resolver],o)}finally{this.__disappearingContent.indexOf(o)>-1&&ue(o.children)}}}__runOnAfterEnterCallbacks(e){for(let t=e.__divergedChainIndex;t<e.chain.length&&this.__isLatestRender(e);t++){const n=e.chain[t].element||{},o=R(e,e.chain[t].route);fe(n.onAfterEnter,[o,{},e.resolver],n)}}__animateIfNeeded(e){const t=(this.__disappearingContent||[])[0],n=(this.__appearingContent||[])[0],o=[],r=e.chain;let s;for(let l=r.length;l>0;l--)if(r[l-1].route.animate){s=r[l-1].route.animate;break}if(t&&n&&s){const l=$e(s)&&s.leave||"leaving",a=$e(s)&&s.enter||"entering";o.push(Nt(t,l)),o.push(Nt(n,a))}return Promise.all(o).then(()=>e)}subscribe(){window.addEventListener("vaadin-router-go",this.__navigationEventHandler)}unsubscribe(){window.removeEventListener("vaadin-router-go",this.__navigationEventHandler)}__onNavigationEvent(e){const{pathname:t,search:n,hash:o}=e?e.detail:window.location;k(this.__normalizePathname(t))&&(e&&e.preventDefault&&e.preventDefault(),this.render({pathname:t,search:n,hash:o},!0))}static setTriggers(...e){Ki(e)}urlForName(e,t){return this.__urlForName||(this.__urlForName=qi(this)),me(this.__urlForName(e,t),this)}urlForPath(e,t){return me(V.pathToRegexp.compile(e)(t),this)}static go(e){const{pathname:t,search:n,hash:o}=k(e)?this.__createUrl(e,"http://a"):e;return ie("go",{pathname:t,search:n,hash:o})}}const tn=/\/\*[\*!]\s+vaadin-dev-mode:start([\s\S]*)vaadin-dev-mode:end\s+\*\*\//i,ge=window.Vaadin&&window.Vaadin.Flow&&window.Vaadin.Flow.clients;function nn(){function i(){return!0}return ri(i)}function on(){try{return rn()?!0:sn()?ge?!an():!nn():!1}catch{return!1}}function rn(){return localStorage.getItem("vaadin.developmentmode.force")}function sn(){return["localhost","127.0.0.1"].indexOf(window.location.hostname)>=0}function an(){return!!(ge&&Object.keys(ge).map(e=>ge[e]).filter(e=>e.productionMode).length>0)}function ri(i,e){if(typeof i!="function")return;const t=tn.exec(i.toString());if(t)try{i=new Function(t[1])}catch(n){console.log("vaadin-development-mode-detector: uncommentAndRun() failed",n)}return i(e)}window.Vaadin=window.Vaadin||{};const Lt=function(i,e){if(window.Vaadin.developmentMode)return ri(i,e)};window.Vaadin.developmentMode===void 0&&(window.Vaadin.developmentMode=on());function ln(){}const dn=function(){if(typeof Lt=="function")return Lt(ln)};window.Vaadin=window.Vaadin||{};window.Vaadin.registrations=window.Vaadin.registrations||[];window.Vaadin.registrations.push({is:"@vaadin/router",version:"1.7.4"});dn();V.NavigationTrigger={POPSTATE:ki,CLICK:Ri};var Pe,b;(function(i){i.CONNECTED="connected",i.LOADING="loading",i.RECONNECTING="reconnecting",i.CONNECTION_LOST="connection-lost"})(b||(b={}));class cn{constructor(e){this.stateChangeListeners=new Set,this.loadingCount=0,this.connectionState=e,this.serviceWorkerMessageListener=this.serviceWorkerMessageListener.bind(this),navigator.serviceWorker&&(navigator.serviceWorker.addEventListener("message",this.serviceWorkerMessageListener),navigator.serviceWorker.ready.then(t=>{var n;(n=t==null?void 0:t.active)===null||n===void 0||n.postMessage({method:"Vaadin.ServiceWorker.isConnectionLost",id:"Vaadin.ServiceWorker.isConnectionLost"})}))}addStateChangeListener(e){this.stateChangeListeners.add(e)}removeStateChangeListener(e){this.stateChangeListeners.delete(e)}loadingStarted(){this.state=b.LOADING,this.loadingCount+=1}loadingFinished(){this.decreaseLoadingCount(b.CONNECTED)}loadingFailed(){this.decreaseLoadingCount(b.CONNECTION_LOST)}decreaseLoadingCount(e){this.loadingCount>0&&(this.loadingCount-=1,this.loadingCount===0&&(this.state=e))}get state(){return this.connectionState}set state(e){if(e!==this.connectionState){const t=this.connectionState;this.connectionState=e,this.loadingCount=0;for(const n of this.stateChangeListeners)n(t,this.connectionState)}}get online(){return this.connectionState===b.CONNECTED||this.connectionState===b.LOADING}get offline(){return!this.online}serviceWorkerMessageListener(e){typeof e.data=="object"&&e.data.id==="Vaadin.ServiceWorker.isConnectionLost"&&(e.data.result===!0&&(this.state=b.CONNECTION_LOST),navigator.serviceWorker.removeEventListener("message",this.serviceWorkerMessageListener))}}const hn=i=>!!(i==="localhost"||i==="[::1]"||i.match(/^127\.\d+\.\d+\.\d+$/)),pe=window;if(!(!((Pe=pe.Vaadin)===null||Pe===void 0)&&Pe.connectionState)){let i;hn(window.location.hostname)?i=!0:i=navigator.onLine,pe.Vaadin=pe.Vaadin||{},pe.Vaadin.connectionState=new cn(i?b.CONNECTED:b.CONNECTION_LOST)}function $(i,e,t,n){var o=arguments.length,r=o<3?e:n===null?n=Object.getOwnPropertyDescriptor(e,t):n,s;if(typeof Reflect=="object"&&typeof Reflect.decorate=="function")r=Reflect.decorate(i,e,t,n);else for(var l=i.length-1;l>=0;l--)(s=i[l])&&(r=(o<3?s(r):o>3?s(e,t,r):s(e,t))||r);return o>3&&r&&Object.defineProperty(e,t,r),r}/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const un=!1,ve=window,lt=ve.ShadowRoot&&(ve.ShadyCSS===void 0||ve.ShadyCSS.nativeShadow)&&"adoptedStyleSheets"in Document.prototype&&"replace"in CSSStyleSheet.prototype,dt=Symbol(),It=new WeakMap;class si{constructor(e,t,n){if(this._$cssResult$=!0,n!==dt)throw new Error("CSSResult is not constructable. Use `unsafeCSS` or `css` instead.");this.cssText=e,this._strings=t}get styleSheet(){let e=this._styleSheet;const t=this._strings;if(lt&&e===void 0){const n=t!==void 0&&t.length===1;n&&(e=It.get(t)),e===void 0&&((this._styleSheet=e=new CSSStyleSheet).replaceSync(this.cssText),n&&It.set(t,e))}return e}toString(){return this.cssText}}const pn=i=>{if(i._$cssResult$===!0)return i.cssText;if(typeof i=="number")return i;throw new Error(`Value passed to 'css' function must be a 'css' function result: ${i}. Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security.`)},fn=i=>new si(typeof i=="string"?i:String(i),void 0,dt),x=(i,...e)=>{const t=i.length===1?i[0]:e.reduce((n,o,r)=>n+pn(o)+i[r+1],i[0]);return new si(t,i,dt)},mn=(i,e)=>{lt?i.adoptedStyleSheets=e.map(t=>t instanceof CSSStyleSheet?t:t.styleSheet):e.forEach(t=>{const n=document.createElement("style"),o=ve.litNonce;o!==void 0&&n.setAttribute("nonce",o),n.textContent=t.cssText,i.appendChild(n)})},gn=i=>{let e="";for(const t of i.cssRules)e+=t.cssText;return fn(e)},Pt=lt||un?i=>i:i=>i instanceof CSSStyleSheet?gn(i):i;/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */var Oe,De,Ve,ai;const I=window;let li,M;const Ot=I.trustedTypes,vn=Ot?Ot.emptyScript:"",_e=I.reactiveElementPolyfillSupportDevMode;{const i=(Oe=I.litIssuedWarnings)!==null&&Oe!==void 0?Oe:I.litIssuedWarnings=new Set;M=(e,t)=>{t+=` See https://lit.dev/msg/${e} for more information.`,i.has(t)||(console.warn(t),i.add(t))},M("dev-mode","Lit is in dev mode. Not recommended for production!"),!((De=I.ShadyDOM)===null||De===void 0)&&De.inUse&&_e===void 0&&M("polyfill-support-missing","Shadow DOM is being polyfilled via `ShadyDOM` but the `polyfill-support` module has not been loaded."),li=e=>({then:(t,n)=>{M("request-update-promise",`The \`requestUpdate\` method should no longer return a Promise but does so on \`${e}\`. Use \`updateComplete\` instead.`),t!==void 0&&t(!1)}})}const Me=i=>{I.emitLitDebugLogEvents&&I.dispatchEvent(new CustomEvent("lit-debug",{detail:i}))},di=(i,e)=>i,nt={toAttribute(i,e){switch(e){case Boolean:i=i?vn:null;break;case Object:case Array:i=i==null?i:JSON.stringify(i);break}return i},fromAttribute(i,e){let t=i;switch(e){case Boolean:t=i!==null;break;case Number:t=i===null?null:Number(i);break;case Object:case Array:try{t=JSON.parse(i)}catch{t=null}break}return t}},ci=(i,e)=>e!==i&&(e===e||i===i),Ue={attribute:!0,type:String,converter:nt,reflect:!1,hasChanged:ci},ot="finalized";class P extends HTMLElement{constructor(){super(),this.__instanceProperties=new Map,this.isUpdatePending=!1,this.hasUpdated=!1,this.__reflectingProperty=null,this._initialize()}static addInitializer(e){var t;this.finalize(),((t=this._initializers)!==null&&t!==void 0?t:this._initializers=[]).push(e)}static get observedAttributes(){this.finalize();const e=[];return this.elementProperties.forEach((t,n)=>{const o=this.__attributeNameForProperty(n,t);o!==void 0&&(this.__attributeToPropertyMap.set(o,n),e.push(o))}),e}static createProperty(e,t=Ue){var n;if(t.state&&(t.attribute=!1),this.finalize(),this.elementProperties.set(e,t),!t.noAccessor&&!this.prototype.hasOwnProperty(e)){const o=typeof e=="symbol"?Symbol():`__${e}`,r=this.getPropertyDescriptor(e,o,t);r!==void 0&&(Object.defineProperty(this.prototype,e,r),this.hasOwnProperty("__reactivePropertyKeys")||(this.__reactivePropertyKeys=new Set((n=this.__reactivePropertyKeys)!==null&&n!==void 0?n:[])),this.__reactivePropertyKeys.add(e))}}static getPropertyDescriptor(e,t,n){return{get(){return this[t]},set(o){const r=this[e];this[t]=o,this.requestUpdate(e,r,n)},configurable:!0,enumerable:!0}}static getPropertyOptions(e){return this.elementProperties.get(e)||Ue}static finalize(){if(this.hasOwnProperty(ot))return!1;this[ot]=!0;const e=Object.getPrototypeOf(this);if(e.finalize(),e._initializers!==void 0&&(this._initializers=[...e._initializers]),this.elementProperties=new Map(e.elementProperties),this.__attributeToPropertyMap=new Map,this.hasOwnProperty(di("properties"))){const t=this.properties,n=[...Object.getOwnPropertyNames(t),...Object.getOwnPropertySymbols(t)];for(const o of n)this.createProperty(o,t[o])}this.elementStyles=this.finalizeStyles(this.styles);{const t=(n,o=!1)=>{this.prototype.hasOwnProperty(n)&&M(o?"renamed-api":"removed-api",`\`${n}\` is implemented on class ${this.name}. It has been ${o?"renamed":"removed"} in this version of LitElement.`)};t("initialize"),t("requestUpdateInternal"),t("_getUpdateComplete",!0)}return!0}static finalizeStyles(e){const t=[];if(Array.isArray(e)){const n=new Set(e.flat(1/0).reverse());for(const o of n)t.unshift(Pt(o))}else e!==void 0&&t.push(Pt(e));return t}static __attributeNameForProperty(e,t){const n=t.attribute;return n===!1?void 0:typeof n=="string"?n:typeof e=="string"?e.toLowerCase():void 0}_initialize(){var e;this.__updatePromise=new Promise(t=>this.enableUpdating=t),this._$changedProperties=new Map,this.__saveInstanceProperties(),this.requestUpdate(),(e=this.constructor._initializers)===null||e===void 0||e.forEach(t=>t(this))}addController(e){var t,n;((t=this.__controllers)!==null&&t!==void 0?t:this.__controllers=[]).push(e),this.renderRoot!==void 0&&this.isConnected&&((n=e.hostConnected)===null||n===void 0||n.call(e))}removeController(e){var t;(t=this.__controllers)===null||t===void 0||t.splice(this.__controllers.indexOf(e)>>>0,1)}__saveInstanceProperties(){this.constructor.elementProperties.forEach((e,t)=>{this.hasOwnProperty(t)&&(this.__instanceProperties.set(t,this[t]),delete this[t])})}createRenderRoot(){var e;const t=(e=this.shadowRoot)!==null&&e!==void 0?e:this.attachShadow(this.constructor.shadowRootOptions);return mn(t,this.constructor.elementStyles),t}connectedCallback(){var e;this.renderRoot===void 0&&(this.renderRoot=this.createRenderRoot()),this.enableUpdating(!0),(e=this.__controllers)===null||e===void 0||e.forEach(t=>{var n;return(n=t.hostConnected)===null||n===void 0?void 0:n.call(t)})}enableUpdating(e){}disconnectedCallback(){var e;(e=this.__controllers)===null||e===void 0||e.forEach(t=>{var n;return(n=t.hostDisconnected)===null||n===void 0?void 0:n.call(t)})}attributeChangedCallback(e,t,n){this._$attributeToProperty(e,n)}__propertyToAttribute(e,t,n=Ue){var o;const r=this.constructor.__attributeNameForProperty(e,n);if(r!==void 0&&n.reflect===!0){const l=(((o=n.converter)===null||o===void 0?void 0:o.toAttribute)!==void 0?n.converter:nt).toAttribute(t,n.type);this.constructor.enabledWarnings.indexOf("migration")>=0&&l===void 0&&M("undefined-attribute-value",`The attribute value for the ${e} property is undefined on element ${this.localName}. The attribute will be removed, but in the previous version of \`ReactiveElement\`, the attribute would not have changed.`),this.__reflectingProperty=e,l==null?this.removeAttribute(r):this.setAttribute(r,l),this.__reflectingProperty=null}}_$attributeToProperty(e,t){var n;const o=this.constructor,r=o.__attributeToPropertyMap.get(e);if(r!==void 0&&this.__reflectingProperty!==r){const s=o.getPropertyOptions(r),l=typeof s.converter=="function"?{fromAttribute:s.converter}:((n=s.converter)===null||n===void 0?void 0:n.fromAttribute)!==void 0?s.converter:nt;this.__reflectingProperty=r,this[r]=l.fromAttribute(t,s.type),this.__reflectingProperty=null}}requestUpdate(e,t,n){let o=!0;return e!==void 0&&(n=n||this.constructor.getPropertyOptions(e),(n.hasChanged||ci)(this[e],t)?(this._$changedProperties.has(e)||this._$changedProperties.set(e,t),n.reflect===!0&&this.__reflectingProperty!==e&&(this.__reflectingProperties===void 0&&(this.__reflectingProperties=new Map),this.__reflectingProperties.set(e,n))):o=!1),!this.isUpdatePending&&o&&(this.__updatePromise=this.__enqueueUpdate()),li(this.localName)}async __enqueueUpdate(){this.isUpdatePending=!0;try{await this.__updatePromise}catch(t){Promise.reject(t)}const e=this.scheduleUpdate();return e!=null&&await e,!this.isUpdatePending}scheduleUpdate(){return this.performUpdate()}performUpdate(){var e,t;if(!this.isUpdatePending)return;if(Me==null||Me({kind:"update"}),!this.hasUpdated){const r=[];if((e=this.constructor.__reactivePropertyKeys)===null||e===void 0||e.forEach(s=>{var l;this.hasOwnProperty(s)&&!(!((l=this.__instanceProperties)===null||l===void 0)&&l.has(s))&&r.push(s)}),r.length)throw new Error(`The following properties on element ${this.localName} will not trigger updates as expected because they are set using class fields: ${r.join(", ")}. Native class fields and some compiled output will overwrite accessors used for detecting changes. See https://lit.dev/msg/class-field-shadowing for more information.`)}this.__instanceProperties&&(this.__instanceProperties.forEach((r,s)=>this[s]=r),this.__instanceProperties=void 0);let n=!1;const o=this._$changedProperties;try{n=this.shouldUpdate(o),n?(this.willUpdate(o),(t=this.__controllers)===null||t===void 0||t.forEach(r=>{var s;return(s=r.hostUpdate)===null||s===void 0?void 0:s.call(r)}),this.update(o)):this.__markUpdated()}catch(r){throw n=!1,this.__markUpdated(),r}n&&this._$didUpdate(o)}willUpdate(e){}_$didUpdate(e){var t;(t=this.__controllers)===null||t===void 0||t.forEach(n=>{var o;return(o=n.hostUpdated)===null||o===void 0?void 0:o.call(n)}),this.hasUpdated||(this.hasUpdated=!0,this.firstUpdated(e)),this.updated(e),this.isUpdatePending&&this.constructor.enabledWarnings.indexOf("change-in-update")>=0&&M("change-in-update",`Element ${this.localName} scheduled an update (generally because a property was set) after an update completed, causing a new update to be scheduled. This is inefficient and should be avoided unless the next update can only be scheduled as a side effect of the previous update.`)}__markUpdated(){this._$changedProperties=new Map,this.isUpdatePending=!1}get updateComplete(){return this.getUpdateComplete()}getUpdateComplete(){return this.__updatePromise}shouldUpdate(e){return!0}update(e){this.__reflectingProperties!==void 0&&(this.__reflectingProperties.forEach((t,n)=>this.__propertyToAttribute(n,this[n],t)),this.__reflectingProperties=void 0),this.__markUpdated()}updated(e){}firstUpdated(e){}}ai=ot;P[ai]=!0;P.elementProperties=new Map;P.elementStyles=[];P.shadowRootOptions={mode:"open"};_e==null||_e({ReactiveElement:P});{P.enabledWarnings=["change-in-update"];const i=function(e){e.hasOwnProperty(di("enabledWarnings"))||(e.enabledWarnings=e.enabledWarnings.slice())};P.enableWarning=function(e){i(this),this.enabledWarnings.indexOf(e)<0&&this.enabledWarnings.push(e)},P.disableWarning=function(e){i(this);const t=this.enabledWarnings.indexOf(e);t>=0&&this.enabledWarnings.splice(t,1)}}((Ve=I.reactiveElementVersions)!==null&&Ve!==void 0?Ve:I.reactiveElementVersions=[]).push("1.6.1");I.reactiveElementVersions.length>1&&M("multiple-versions","Multiple versions of Lit loaded. Loading multiple versions is not recommended.");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */var Fe,Be,ze,He;const C=window,f=i=>{C.emitLitDebugLogEvents&&C.dispatchEvent(new CustomEvent("lit-debug",{detail:i}))};let _n=0,Te;(Fe=C.litIssuedWarnings)!==null&&Fe!==void 0||(C.litIssuedWarnings=new Set),Te=(i,e)=>{e+=i?` See https://lit.dev/msg/${i} for more information.`:"",C.litIssuedWarnings.has(e)||(console.warn(e),C.litIssuedWarnings.add(e))},Te("dev-mode","Lit is in dev mode. Not recommended for production!");const T=!((Be=C.ShadyDOM)===null||Be===void 0)&&Be.inUse&&((ze=C.ShadyDOM)===null||ze===void 0?void 0:ze.noPatch)===!0?C.ShadyDOM.wrap:i=>i,K=C.trustedTypes,Dt=K?K.createPolicy("lit-html",{createHTML:i=>i}):void 0,yn=i=>i,Ne=(i,e,t)=>yn,bn=i=>{if(z!==Ne)throw new Error("Attempted to overwrite existing lit-html security policy. setSanitizeDOMValueFactory should be called at most once.");z=i},wn=()=>{z=Ne},rt=(i,e,t)=>z(i,e,t),st="$lit$",O=`lit$${String(Math.random()).slice(9)}$`,hi="?"+O,Sn=`<${hi}>`,Y=document,re=()=>Y.createComment(""),se=i=>i===null||typeof i!="object"&&typeof i!="function",ui=Array.isArray,En=i=>ui(i)||typeof(i==null?void 0:i[Symbol.iterator])=="function",We=`[ 	
\f\r]`,Cn=`[^ 	
\f\r"'\`<>=]`,$n=`[^\\s"'>=/]`,ee=/<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g,Vt=1,je=2,Tn=3,Mt=/-->/g,Ut=/>/g,U=new RegExp(`>|${We}(?:(${$n}+)(${We}*=${We}*(?:${Cn}|("|')|))|$)`,"g"),xn=0,Ft=1,Rn=2,Bt=3,Ge=/'/g,qe=/"/g,pi=/^(?:script|style|textarea|title)$/i,Nn=1,xe=2,ct=1,Re=2,kn=3,An=4,Ln=5,ht=6,In=7,fi=i=>(e,...t)=>(e.some(n=>n===void 0)&&console.warn(`Some template strings are undefined.
This is probably caused by illegal octal escape sequences.`),{_$litType$:i,strings:e,values:t}),E=fi(Nn),uo=fi(xe),B=Symbol.for("lit-noChange"),y=Symbol.for("lit-nothing"),zt=new WeakMap,q=Y.createTreeWalker(Y,129,null,!1);let z=Ne;const Pn=(i,e)=>{const t=i.length-1,n=[];let o=e===xe?"<svg>":"",r,s=ee;for(let a=0;a<t;a++){const d=i[a];let c=-1,u,h=0,p;for(;h<d.length&&(s.lastIndex=h,p=s.exec(d),p!==null);)if(h=s.lastIndex,s===ee){if(p[Vt]==="!--")s=Mt;else if(p[Vt]!==void 0)s=Ut;else if(p[je]!==void 0)pi.test(p[je])&&(r=new RegExp(`</${p[je]}`,"g")),s=U;else if(p[Tn]!==void 0)throw new Error("Bindings in tag names are not supported. Please use static templates instead. See https://lit.dev/docs/templates/expressions/#static-expressions")}else s===U?p[xn]===">"?(s=r??ee,c=-1):p[Ft]===void 0?c=-2:(c=s.lastIndex-p[Rn].length,u=p[Ft],s=p[Bt]===void 0?U:p[Bt]==='"'?qe:Ge):s===qe||s===Ge?s=U:s===Mt||s===Ut?s=ee:(s=U,r=void 0);console.assert(c===-1||s===U||s===Ge||s===qe,"unexpected parse state B");const W=s===U&&i[a+1].startsWith("/>")?" ":"";o+=s===ee?d+Sn:c>=0?(n.push(u),d.slice(0,c)+st+d.slice(c)+O+W):d+O+(c===-2?(n.push(void 0),a):W)}const l=o+(i[t]||"<?>")+(e===xe?"</svg>":"");if(!Array.isArray(i)||!i.hasOwnProperty("raw")){let a="invalid template strings array";throw a=`
          Internal Error: expected template strings to be an array
          with a 'raw' field. Faking a template strings array by
          calling html or svg like an ordinary function is effectively
          the same as calling unsafeHtml and can lead to major security
          issues, e.g. opening your code up to XSS attacks.

          If you're using the html or svg tagged template functions normally
          and still seeing this error, please file a bug at
          https://github.com/lit/lit/issues/new?template=bug_report.md
          and include information about your build tooling, if any.
        `.trim().replace(/\n */g,`
`),new Error(a)}return[Dt!==void 0?Dt.createHTML(l):l,n]};class ae{constructor({strings:e,["_$litType$"]:t},n){this.parts=[];let o,r=0,s=0;const l=e.length-1,a=this.parts,[d,c]=Pn(e,t);if(this.el=ae.createElement(d,n),q.currentNode=this.el.content,t===xe){const u=this.el.content,h=u.firstChild;h.remove(),u.append(...h.childNodes)}for(;(o=q.nextNode())!==null&&a.length<l;){if(o.nodeType===1){{const u=o.localName;if(/^(?:textarea|template)$/i.test(u)&&o.innerHTML.includes(O)){const h=`Expressions are not supported inside \`${u}\` elements. See https://lit.dev/msg/expression-in-${u} for more information.`;if(u==="template")throw new Error(h);Te("",h)}}if(o.hasAttributes()){const u=[];for(const h of o.getAttributeNames())if(h.endsWith(st)||h.startsWith(O)){const p=c[s++];if(u.push(h),p!==void 0){const Ae=o.getAttribute(p.toLowerCase()+st).split(O),j=/([.?@])?(.*)/.exec(p);a.push({type:ct,index:r,name:j[2],strings:Ae,ctor:j[1]==="."?Dn:j[1]==="?"?Mn:j[1]==="@"?Un:ke})}else a.push({type:ht,index:r})}for(const h of u)o.removeAttribute(h)}if(pi.test(o.tagName)){const u=o.textContent.split(O),h=u.length-1;if(h>0){o.textContent=K?K.emptyScript:"";for(let p=0;p<h;p++)o.append(u[p],re()),q.nextNode(),a.push({type:Re,index:++r});o.append(u[h],re())}}}else if(o.nodeType===8)if(o.data===hi)a.push({type:Re,index:r});else{let h=-1;for(;(h=o.data.indexOf(O,h+1))!==-1;)a.push({type:In,index:r}),h+=O.length-1}r++}f==null||f({kind:"template prep",template:this,clonableTemplate:this.el,parts:this.parts,strings:e})}static createElement(e,t){const n=Y.createElement("template");return n.innerHTML=e,n}}function J(i,e,t=i,n){var o,r,s,l;if(e===B)return e;let a=n!==void 0?(o=t.__directives)===null||o===void 0?void 0:o[n]:t.__directive;const d=se(e)?void 0:e._$litDirective$;return(a==null?void 0:a.constructor)!==d&&((r=a==null?void 0:a._$notifyDirectiveConnectionChanged)===null||r===void 0||r.call(a,!1),d===void 0?a=void 0:(a=new d(i),a._$initialize(i,t,n)),n!==void 0?((s=(l=t).__directives)!==null&&s!==void 0?s:l.__directives=[])[n]=a:t.__directive=a),a!==void 0&&(e=J(i,a._$resolve(i,e.values),a,n)),e}class On{constructor(e,t){this._$parts=[],this._$disconnectableChildren=void 0,this._$template=e,this._$parent=t}get parentNode(){return this._$parent.parentNode}get _$isConnected(){return this._$parent._$isConnected}_clone(e){var t;const{el:{content:n},parts:o}=this._$template,r=((t=e==null?void 0:e.creationScope)!==null&&t!==void 0?t:Y).importNode(n,!0);q.currentNode=r;let s=q.nextNode(),l=0,a=0,d=o[0];for(;d!==void 0;){if(l===d.index){let c;d.type===Re?c=new ce(s,s.nextSibling,this,e):d.type===ct?c=new d.ctor(s,d.name,d.strings,this,e):d.type===ht&&(c=new Fn(s,this,e)),this._$parts.push(c),d=o[++a]}l!==(d==null?void 0:d.index)&&(s=q.nextNode(),l++)}return r}_update(e){let t=0;for(const n of this._$parts)n!==void 0&&(f==null||f({kind:"set part",part:n,value:e[t],valueIndex:t,values:e,templateInstance:this}),n.strings!==void 0?(n._$setValue(e,n,t),t+=n.strings.length-2):n._$setValue(e[t])),t++}}class ce{constructor(e,t,n,o){var r;this.type=Re,this._$committedValue=y,this._$disconnectableChildren=void 0,this._$startNode=e,this._$endNode=t,this._$parent=n,this.options=o,this.__isConnected=(r=o==null?void 0:o.isConnected)!==null&&r!==void 0?r:!0,this._textSanitizer=void 0}get _$isConnected(){var e,t;return(t=(e=this._$parent)===null||e===void 0?void 0:e._$isConnected)!==null&&t!==void 0?t:this.__isConnected}get parentNode(){let e=T(this._$startNode).parentNode;const t=this._$parent;return t!==void 0&&(e==null?void 0:e.nodeType)===11&&(e=t.parentNode),e}get startNode(){return this._$startNode}get endNode(){return this._$endNode}_$setValue(e,t=this){var n;if(this.parentNode===null)throw new Error("This `ChildPart` has no `parentNode` and therefore cannot accept a value. This likely means the element containing the part was manipulated in an unsupported way outside of Lit's control such that the part's marker nodes were ejected from DOM. For example, setting the element's `innerHTML` or `textContent` can do this.");if(e=J(this,e,t),se(e))e===y||e==null||e===""?(this._$committedValue!==y&&(f==null||f({kind:"commit nothing to child",start:this._$startNode,end:this._$endNode,parent:this._$parent,options:this.options}),this._$clear()),this._$committedValue=y):e!==this._$committedValue&&e!==B&&this._commitText(e);else if(e._$litType$!==void 0)this._commitTemplateResult(e);else if(e.nodeType!==void 0){if(((n=this.options)===null||n===void 0?void 0:n.host)===e){this._commitText("[probable mistake: rendered a template's host in itself (commonly caused by writing ${this} in a template]"),console.warn("Attempted to render the template host",e,"inside itself. This is almost always a mistake, and in dev mode ","we render some warning text. In production however, we'll ","render it, which will usually result in an error, and sometimes ","in the element disappearing from the DOM.");return}this._commitNode(e)}else En(e)?this._commitIterable(e):this._commitText(e)}_insert(e){return T(T(this._$startNode).parentNode).insertBefore(e,this._$endNode)}_commitNode(e){var t;if(this._$committedValue!==e){if(this._$clear(),z!==Ne){const n=(t=this._$startNode.parentNode)===null||t===void 0?void 0:t.nodeName;if(n==="STYLE"||n==="SCRIPT"){let o="Forbidden";throw n==="STYLE"?o="Lit does not support binding inside style nodes. This is a security risk, as style injection attacks can exfiltrate data and spoof UIs. Consider instead using css`...` literals to compose styles, and make do dynamic styling with css custom properties, ::parts, <slot>s, and by mutating the DOM rather than stylesheets.":o="Lit does not support binding inside script nodes. This is a security risk, as it could allow arbitrary code execution.",new Error(o)}}f==null||f({kind:"commit node",start:this._$startNode,parent:this._$parent,value:e,options:this.options}),this._$committedValue=this._insert(e)}}_commitText(e){if(this._$committedValue!==y&&se(this._$committedValue)){const t=T(this._$startNode).nextSibling;this._textSanitizer===void 0&&(this._textSanitizer=rt(t,"data","property")),e=this._textSanitizer(e),f==null||f({kind:"commit text",node:t,value:e,options:this.options}),t.data=e}else{const t=Y.createTextNode("");this._commitNode(t),this._textSanitizer===void 0&&(this._textSanitizer=rt(t,"data","property")),e=this._textSanitizer(e),f==null||f({kind:"commit text",node:t,value:e,options:this.options}),t.data=e}this._$committedValue=e}_commitTemplateResult(e){var t;const{values:n,["_$litType$"]:o}=e,r=typeof o=="number"?this._$getTemplate(e):(o.el===void 0&&(o.el=ae.createElement(o.h,this.options)),o);if(((t=this._$committedValue)===null||t===void 0?void 0:t._$template)===r)f==null||f({kind:"template updating",template:r,instance:this._$committedValue,parts:this._$committedValue._$parts,options:this.options,values:n}),this._$committedValue._update(n);else{const s=new On(r,this),l=s._clone(this.options);f==null||f({kind:"template instantiated",template:r,instance:s,parts:s._$parts,options:this.options,fragment:l,values:n}),s._update(n),f==null||f({kind:"template instantiated and updated",template:r,instance:s,parts:s._$parts,options:this.options,fragment:l,values:n}),this._commitNode(l),this._$committedValue=s}}_$getTemplate(e){let t=zt.get(e.strings);return t===void 0&&zt.set(e.strings,t=new ae(e)),t}_commitIterable(e){ui(this._$committedValue)||(this._$committedValue=[],this._$clear());const t=this._$committedValue;let n=0,o;for(const r of e)n===t.length?t.push(o=new ce(this._insert(re()),this._insert(re()),this,this.options)):o=t[n],o._$setValue(r),n++;n<t.length&&(this._$clear(o&&T(o._$endNode).nextSibling,n),t.length=n)}_$clear(e=T(this._$startNode).nextSibling,t){var n;for((n=this._$notifyConnectionChanged)===null||n===void 0||n.call(this,!1,!0,t);e&&e!==this._$endNode;){const o=T(e).nextSibling;T(e).remove(),e=o}}setConnected(e){var t;if(this._$parent===void 0)this.__isConnected=e,(t=this._$notifyConnectionChanged)===null||t===void 0||t.call(this,e);else throw new Error("part.setConnected() may only be called on a RootPart returned from render().")}}class ke{constructor(e,t,n,o,r){this.type=ct,this._$committedValue=y,this._$disconnectableChildren=void 0,this.element=e,this.name=t,this._$parent=o,this.options=r,n.length>2||n[0]!==""||n[1]!==""?(this._$committedValue=new Array(n.length-1).fill(new String),this.strings=n):this._$committedValue=y,this._sanitizer=void 0}get tagName(){return this.element.tagName}get _$isConnected(){return this._$parent._$isConnected}_$setValue(e,t=this,n,o){const r=this.strings;let s=!1;if(r===void 0)e=J(this,e,t,0),s=!se(e)||e!==this._$committedValue&&e!==B,s&&(this._$committedValue=e);else{const l=e;e=r[0];let a,d;for(a=0;a<r.length-1;a++)d=J(this,l[n+a],t,a),d===B&&(d=this._$committedValue[a]),s||(s=!se(d)||d!==this._$committedValue[a]),d===y?e=y:e!==y&&(e+=(d??"")+r[a+1]),this._$committedValue[a]=d}s&&!o&&this._commitValue(e)}_commitValue(e){e===y?T(this.element).removeAttribute(this.name):(this._sanitizer===void 0&&(this._sanitizer=z(this.element,this.name,"attribute")),e=this._sanitizer(e??""),f==null||f({kind:"commit attribute",element:this.element,name:this.name,value:e,options:this.options}),T(this.element).setAttribute(this.name,e??""))}}class Dn extends ke{constructor(){super(...arguments),this.type=kn}_commitValue(e){this._sanitizer===void 0&&(this._sanitizer=z(this.element,this.name,"property")),e=this._sanitizer(e),f==null||f({kind:"commit property",element:this.element,name:this.name,value:e,options:this.options}),this.element[this.name]=e===y?void 0:e}}const Vn=K?K.emptyScript:"";class Mn extends ke{constructor(){super(...arguments),this.type=An}_commitValue(e){f==null||f({kind:"commit boolean attribute",element:this.element,name:this.name,value:!!(e&&e!==y),options:this.options}),e&&e!==y?T(this.element).setAttribute(this.name,Vn):T(this.element).removeAttribute(this.name)}}class Un extends ke{constructor(e,t,n,o,r){if(super(e,t,n,o,r),this.type=Ln,this.strings!==void 0)throw new Error(`A \`<${e.localName}>\` has a \`@${t}=...\` listener with invalid content. Event listeners in templates must have exactly one expression and no surrounding text.`)}_$setValue(e,t=this){var n;if(e=(n=J(this,e,t,0))!==null&&n!==void 0?n:y,e===B)return;const o=this._$committedValue,r=e===y&&o!==y||e.capture!==o.capture||e.once!==o.once||e.passive!==o.passive,s=e!==y&&(o===y||r);f==null||f({kind:"commit event listener",element:this.element,name:this.name,value:e,options:this.options,removeListener:r,addListener:s,oldListener:o}),r&&this.element.removeEventListener(this.name,this,o),s&&this.element.addEventListener(this.name,this,e),this._$committedValue=e}handleEvent(e){var t,n;typeof this._$committedValue=="function"?this._$committedValue.call((n=(t=this.options)===null||t===void 0?void 0:t.host)!==null&&n!==void 0?n:this.element,e):this._$committedValue.handleEvent(e)}}class Fn{constructor(e,t,n){this.element=e,this.type=ht,this._$disconnectableChildren=void 0,this._$parent=t,this.options=n}get _$isConnected(){return this._$parent._$isConnected}_$setValue(e){f==null||f({kind:"commit to element binding",element:this.element,value:e,options:this.options}),J(this,e)}}const Ke=C.litHtmlPolyfillSupportDevMode;Ke==null||Ke(ae,ce);((He=C.litHtmlVersions)!==null&&He!==void 0?He:C.litHtmlVersions=[]).push("2.7.2");C.litHtmlVersions.length>1&&Te("multiple-versions","Multiple versions of Lit loaded. Loading multiple versions is not recommended.");const ye=(i,e,t)=>{var n,o;if(e==null)throw new TypeError(`The container to render into may not be ${e}`);const r=_n++,s=(n=t==null?void 0:t.renderBefore)!==null&&n!==void 0?n:e;let l=s._$litPart$;if(f==null||f({kind:"begin render",id:r,value:i,container:e,options:t,part:l}),l===void 0){const a=(o=t==null?void 0:t.renderBefore)!==null&&o!==void 0?o:null;s._$litPart$=l=new ce(e.insertBefore(re(),a),a,void 0,t??{})}return l._$setValue(i),f==null||f({kind:"end render",id:r,value:i,container:e,options:t,part:l}),l};ye.setSanitizer=bn,ye.createSanitizer=rt,ye._testOnlyClearSanitizerFactoryDoNotCallOrElse=wn;/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */var Ye,Je,Xe;let ut;{const i=(Ye=globalThis.litIssuedWarnings)!==null&&Ye!==void 0?Ye:globalThis.litIssuedWarnings=new Set;ut=(e,t)=>{t+=` See https://lit.dev/msg/${e} for more information.`,i.has(t)||(console.warn(t),i.add(t))}}class H extends P{constructor(){super(...arguments),this.renderOptions={host:this},this.__childPart=void 0}createRenderRoot(){var e,t;const n=super.createRenderRoot();return(e=(t=this.renderOptions).renderBefore)!==null&&e!==void 0||(t.renderBefore=n.firstChild),n}update(e){const t=this.render();this.hasUpdated||(this.renderOptions.isConnected=this.isConnected),super.update(e),this.__childPart=ye(t,this.renderRoot,this.renderOptions)}connectedCallback(){var e;super.connectedCallback(),(e=this.__childPart)===null||e===void 0||e.setConnected(!0)}disconnectedCallback(){var e;super.disconnectedCallback(),(e=this.__childPart)===null||e===void 0||e.setConnected(!1)}render(){return B}}H.finalized=!0;H._$litElement$=!0;(Je=globalThis.litElementHydrateSupport)===null||Je===void 0||Je.call(globalThis,{LitElement:H});const Qe=globalThis.litElementPolyfillSupportDevMode;Qe==null||Qe({LitElement:H});H.finalize=function(){if(!P.finalize.call(this))return!1;const e=(t,n,o=!1)=>{if(t.hasOwnProperty(n)){const r=(typeof t=="function"?t:t.constructor).name;ut(o?"renamed-api":"removed-api",`\`${n}\` is implemented on class ${r}. It has been ${o?"renamed":"removed"} in this version of LitElement.`)}};return e(this,"render"),e(this,"getStyles",!0),e(this.prototype,"adoptStyles"),!0};((Xe=globalThis.litElementVersions)!==null&&Xe!==void 0?Xe:globalThis.litElementVersions=[]).push("3.3.1");globalThis.litElementVersions.length>1&&ut("multiple-versions","Multiple versions of Lit loaded. Loading multiple versions is not recommended.");/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Bn=(i,e)=>e.kind==="method"&&e.descriptor&&!("value"in e.descriptor)?{...e,finisher(t){t.createProperty(e.key,i)}}:{kind:"field",key:Symbol(),placement:"own",descriptor:{},originalKey:e.key,initializer(){typeof e.initializer=="function"&&(this[e.key]=e.initializer.call(this))},finisher(t){t.createProperty(e.key,i)}},zn=(i,e,t)=>{e.constructor.createProperty(t,i)};function _(i){return(e,t)=>t!==void 0?zn(i,e,t):Bn(i,e)}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function Z(i){return _({...i,state:!0})}/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Hn=({finisher:i,descriptor:e})=>(t,n)=>{var o;if(n!==void 0){const r=t.constructor;e!==void 0&&Object.defineProperty(t,n,e(n)),i==null||i(r,n)}else{const r=(o=t.originalKey)!==null&&o!==void 0?o:t.key,s=e!=null?{kind:"method",placement:"prototype",key:r,descriptor:e(t.key)}:{...t,key:r};return i!=null&&(s.finisher=function(l){i(l,r)}),s}};/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function Wn(i,e){return Hn({descriptor:t=>{const n={get(){var o,r;return(r=(o=this.renderRoot)===null||o===void 0?void 0:o.querySelector(i))!==null&&r!==void 0?r:null},enumerable:!0,configurable:!0};if(e){const o=typeof t=="symbol"?Symbol():`__${t}`;n.get=function(){var r,s;return this[o]===void 0&&(this[o]=(s=(r=this.renderRoot)===null||r===void 0?void 0:r.querySelector(i))!==null&&s!==void 0?s:null),this[o]}}return n}})}/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */var Ze;const jn=window;((Ze=jn.HTMLSlotElement)===null||Ze===void 0?void 0:Ze.prototype.assignedElements)!=null;/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Gn={ATTRIBUTE:1,CHILD:2,PROPERTY:3,BOOLEAN_ATTRIBUTE:4,EVENT:5,ELEMENT:6},qn=i=>(...e)=>({_$litDirective$:i,values:e});class Kn{constructor(e){}get _$isConnected(){return this._$parent._$isConnected}_$initialize(e,t,n){this.__part=e,this._$parent=t,this.__attributeIndex=n}_$resolve(e,t){return this.update(e,t)}update(e,t){return this.render(...t)}}/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */class Yn extends Kn{constructor(e){var t;if(super(e),e.type!==Gn.ATTRIBUTE||e.name!=="class"||((t=e.strings)===null||t===void 0?void 0:t.length)>2)throw new Error("`classMap()` can only be used in the `class` attribute and must be the only part in the attribute.")}render(e){return" "+Object.keys(e).filter(t=>e[t]).join(" ")+" "}update(e,[t]){var n,o;if(this._previousClasses===void 0){this._previousClasses=new Set,e.strings!==void 0&&(this._staticClasses=new Set(e.strings.join(" ").split(/\s/).filter(s=>s!=="")));for(const s in t)t[s]&&!(!((n=this._staticClasses)===null||n===void 0)&&n.has(s))&&this._previousClasses.add(s);return this.render(t)}const r=e.element.classList;this._previousClasses.forEach(s=>{s in t||(r.remove(s),this._previousClasses.delete(s))});for(const s in t){const l=!!t[s];l!==this._previousClasses.has(s)&&!(!((o=this._staticClasses)===null||o===void 0)&&o.has(s))&&(l?(r.add(s),this._previousClasses.add(s)):(r.remove(s),this._previousClasses.delete(s)))}return B}}const mi=qn(Yn),et="css-loading-indicator";var N;(function(i){i.IDLE="",i.FIRST="first",i.SECOND="second",i.THIRD="third"})(N||(N={}));class w extends H{constructor(){super(),this.firstDelay=450,this.secondDelay=1500,this.thirdDelay=5e3,this.expandedDuration=2e3,this.onlineText="Online",this.offlineText="Connection lost",this.reconnectingText="Connection lost, trying to reconnect...",this.offline=!1,this.reconnecting=!1,this.expanded=!1,this.loading=!1,this.loadingBarState=N.IDLE,this.applyDefaultThemeState=!0,this.firstTimeout=0,this.secondTimeout=0,this.thirdTimeout=0,this.expandedTimeout=0,this.lastMessageState=b.CONNECTED,this.connectionStateListener=()=>{this.expanded=this.updateConnectionState(),this.expandedTimeout=this.timeoutFor(this.expandedTimeout,this.expanded,()=>{this.expanded=!1},this.expandedDuration)}}static create(){var e,t;const n=window;return!((e=n.Vaadin)===null||e===void 0)&&e.connectionIndicator||(n.Vaadin=n.Vaadin||{},n.Vaadin.connectionIndicator=document.createElement("vaadin-connection-indicator"),document.body.appendChild(n.Vaadin.connectionIndicator)),(t=n.Vaadin)===null||t===void 0?void 0:t.connectionIndicator}render(){return E`
      <div class="v-loading-indicator ${this.loadingBarState}" style=${this.getLoadingBarStyle()}></div>

      <div
        class="v-status-message ${mi({active:this.reconnecting})}"
      >
        <span class="text"> ${this.renderMessage()} </span>
      </div>
    `}connectedCallback(){var e;super.connectedCallback();const t=window;!((e=t.Vaadin)===null||e===void 0)&&e.connectionState&&(this.connectionStateStore=t.Vaadin.connectionState,this.connectionStateStore.addStateChangeListener(this.connectionStateListener),this.updateConnectionState()),this.updateTheme()}disconnectedCallback(){super.disconnectedCallback(),this.connectionStateStore&&this.connectionStateStore.removeStateChangeListener(this.connectionStateListener),this.updateTheme()}get applyDefaultTheme(){return this.applyDefaultThemeState}set applyDefaultTheme(e){e!==this.applyDefaultThemeState&&(this.applyDefaultThemeState=e,this.updateTheme())}createRenderRoot(){return this}updateConnectionState(){var e;const t=(e=this.connectionStateStore)===null||e===void 0?void 0:e.state;return this.offline=t===b.CONNECTION_LOST,this.reconnecting=t===b.RECONNECTING,this.updateLoading(t===b.LOADING),this.loading?!1:t!==this.lastMessageState?(this.lastMessageState=t,!0):!1}updateLoading(e){this.loading=e,this.loadingBarState=N.IDLE,this.firstTimeout=this.timeoutFor(this.firstTimeout,e,()=>{this.loadingBarState=N.FIRST},this.firstDelay),this.secondTimeout=this.timeoutFor(this.secondTimeout,e,()=>{this.loadingBarState=N.SECOND},this.secondDelay),this.thirdTimeout=this.timeoutFor(this.thirdTimeout,e,()=>{this.loadingBarState=N.THIRD},this.thirdDelay)}renderMessage(){return this.reconnecting?this.reconnectingText:this.offline?this.offlineText:this.onlineText}updateTheme(){if(this.applyDefaultThemeState&&this.isConnected){if(!document.getElementById(et)){const e=document.createElement("style");e.id=et,e.textContent=this.getDefaultStyle(),document.head.appendChild(e)}}else{const e=document.getElementById(et);e&&document.head.removeChild(e)}}getDefaultStyle(){return`
      @keyframes v-progress-start {
        0% {
          width: 0%;
        }
        100% {
          width: 50%;
        }
      }
      @keyframes v-progress-delay {
        0% {
          width: 50%;
        }
        100% {
          width: 90%;
        }
      }
      @keyframes v-progress-wait {
        0% {
          width: 90%;
          height: 4px;
        }
        3% {
          width: 91%;
          height: 7px;
        }
        100% {
          width: 96%;
          height: 7px;
        }
      }
      @keyframes v-progress-wait-pulse {
        0% {
          opacity: 1;
        }
        50% {
          opacity: 0.1;
        }
        100% {
          opacity: 1;
        }
      }
      .v-loading-indicator,
      .v-status-message {
        position: fixed;
        z-index: 251;
        left: 0;
        right: auto;
        top: 0;
        background-color: var(--lumo-primary-color, var(--material-primary-color, blue));
        transition: none;
      }
      .v-loading-indicator {
        width: 50%;
        height: 4px;
        opacity: 1;
        pointer-events: none;
        animation: v-progress-start 1000ms 200ms both;
      }
      .v-loading-indicator[style*='none'] {
        display: block !important;
        width: 100%;
        opacity: 0;
        animation: none;
        transition: opacity 500ms 300ms, width 300ms;
      }
      .v-loading-indicator.second {
        width: 90%;
        animation: v-progress-delay 3.8s forwards;
      }
      .v-loading-indicator.third {
        width: 96%;
        animation: v-progress-wait 5s forwards, v-progress-wait-pulse 1s 4s infinite backwards;
      }

      vaadin-connection-indicator[offline] .v-loading-indicator,
      vaadin-connection-indicator[reconnecting] .v-loading-indicator {
        display: none;
      }

      .v-status-message {
        opacity: 0;
        width: 100%;
        max-height: var(--status-height-collapsed, 8px);
        overflow: hidden;
        background-color: var(--status-bg-color-online, var(--lumo-primary-color, var(--material-primary-color, blue)));
        color: var(
          --status-text-color-online,
          var(--lumo-primary-contrast-color, var(--material-primary-contrast-color, #fff))
        );
        font-size: 0.75rem;
        font-weight: 600;
        line-height: 1;
        transition: all 0.5s;
        padding: 0 0.5em;
      }

      vaadin-connection-indicator[offline] .v-status-message,
      vaadin-connection-indicator[reconnecting] .v-status-message {
        opacity: 1;
        background-color: var(--status-bg-color-offline, var(--lumo-shade, #333));
        color: var(
          --status-text-color-offline,
          var(--lumo-primary-contrast-color, var(--material-primary-contrast-color, #fff))
        );
        background-image: repeating-linear-gradient(
          45deg,
          rgba(255, 255, 255, 0),
          rgba(255, 255, 255, 0) 10px,
          rgba(255, 255, 255, 0.1) 10px,
          rgba(255, 255, 255, 0.1) 20px
        );
      }

      vaadin-connection-indicator[reconnecting] .v-status-message {
        animation: show-reconnecting-status 2s;
      }

      vaadin-connection-indicator[offline] .v-status-message:hover,
      vaadin-connection-indicator[reconnecting] .v-status-message:hover,
      vaadin-connection-indicator[expanded] .v-status-message {
        max-height: var(--status-height, 1.75rem);
      }

      vaadin-connection-indicator[expanded] .v-status-message {
        opacity: 1;
      }

      .v-status-message span {
        display: flex;
        align-items: center;
        justify-content: center;
        height: var(--status-height, 1.75rem);
      }

      vaadin-connection-indicator[reconnecting] .v-status-message span::before {
        content: '';
        width: 1em;
        height: 1em;
        border-top: 2px solid
          var(--status-spinner-color, var(--lumo-primary-color, var(--material-primary-color, blue)));
        border-left: 2px solid
          var(--status-spinner-color, var(--lumo-primary-color, var(--material-primary-color, blue)));
        border-right: 2px solid transparent;
        border-bottom: 2px solid transparent;
        border-radius: 50%;
        box-sizing: border-box;
        animation: v-spin 0.4s linear infinite;
        margin: 0 0.5em;
      }

      @keyframes v-spin {
        100% {
          transform: rotate(360deg);
        }
      }
    `}getLoadingBarStyle(){switch(this.loadingBarState){case N.IDLE:return"display: none";case N.FIRST:case N.SECOND:case N.THIRD:return"display: block";default:return""}}timeoutFor(e,t,n,o){return e!==0&&window.clearTimeout(e),t?window.setTimeout(n,o):0}static get instance(){return w.create()}}$([_({type:Number})],w.prototype,"firstDelay",void 0);$([_({type:Number})],w.prototype,"secondDelay",void 0);$([_({type:Number})],w.prototype,"thirdDelay",void 0);$([_({type:Number})],w.prototype,"expandedDuration",void 0);$([_({type:String})],w.prototype,"onlineText",void 0);$([_({type:String})],w.prototype,"offlineText",void 0);$([_({type:String})],w.prototype,"reconnectingText",void 0);$([_({type:Boolean,reflect:!0})],w.prototype,"offline",void 0);$([_({type:Boolean,reflect:!0})],w.prototype,"reconnecting",void 0);$([_({type:Boolean,reflect:!0})],w.prototype,"expanded",void 0);$([_({type:Boolean,reflect:!0})],w.prototype,"loading",void 0);$([_({type:String})],w.prototype,"loadingBarState",void 0);$([_({type:Boolean})],w.prototype,"applyDefaultTheme",null);customElements.get("vaadin-connection-indicator")===void 0&&customElements.define("vaadin-connection-indicator",w);w.instance;const le=window;le.Vaadin=le.Vaadin||{};le.Vaadin.registrations=le.Vaadin.registrations||[];le.Vaadin.registrations.push({is:"@vaadin/common-frontend",version:"0.0.18"});class Ht extends Error{}const te=window.document.body,v=window;class Jn{constructor(e){this.response=void 0,this.pathname="",this.isActive=!1,this.baseRegex=/^\//,te.$=te.$||[],this.config=e||{},v.Vaadin=v.Vaadin||{},v.Vaadin.Flow=v.Vaadin.Flow||{},v.Vaadin.Flow.clients={TypeScript:{isActive:()=>this.isActive}};const t=document.head.querySelector("base");this.baseRegex=new RegExp(`^${(document.baseURI||t&&t.href||"/").replace(/^https?:\/\/[^/]+/i,"")}`),this.appShellTitle=document.title,this.addConnectionIndicator()}get serverSideRoutes(){return[{path:"(.*)",action:this.action}]}loadingStarted(){this.isActive=!0,v.Vaadin.connectionState.loadingStarted()}loadingFinished(){this.isActive=!1,v.Vaadin.connectionState.loadingFinished()}get action(){return async e=>{if(this.pathname=e.pathname,v.Vaadin.connectionState.online)try{await this.flowInit()}catch(t){if(t instanceof Ht)return v.Vaadin.connectionState.state=b.CONNECTION_LOST,this.offlineStubAction();throw t}else return this.offlineStubAction();return this.container.onBeforeEnter=(t,n)=>this.flowNavigate(t,n),this.container.onBeforeLeave=(t,n)=>this.flowLeave(t,n),this.container}}async flowLeave(e,t){const{connectionState:n}=v.Vaadin;return this.pathname===e.pathname||!this.isFlowClientLoaded()||n.offline?Promise.resolve({}):new Promise(o=>{this.loadingStarted(),this.container.serverConnected=r=>{o(t&&r?t.prevent():{}),this.loadingFinished()},te.$server.leaveNavigation(this.getFlowRoutePath(e),this.getFlowRouteQuery(e))})}async flowNavigate(e,t){return this.response?new Promise(n=>{this.loadingStarted(),this.container.serverConnected=(o,r)=>{t&&o?n(t.prevent()):t&&t.redirect&&r?n(t.redirect(r.pathname)):(this.container.style.display="",n(this.container)),this.loadingFinished()},te.$server.connectClient(this.container.localName,this.container.id,this.getFlowRoutePath(e),this.getFlowRouteQuery(e),this.appShellTitle,history.state)}):Promise.resolve(this.container)}getFlowRoutePath(e){return decodeURIComponent(e.pathname).replace(this.baseRegex,"")}getFlowRouteQuery(e){return e.search&&e.search.substring(1)||""}async flowInit(e=!1){if(!this.isFlowClientLoaded()){this.loadingStarted(),this.response=await this.flowInitUi(e),this.response.appConfig.clientRouting=!e;const{pushScript:t,appConfig:n}=this.response;typeof t=="string"&&await this.loadScript(t);const{appId:o}=n;await(await we(()=>import("./FlowBootstrap-feff2646.js"),[],import.meta.url)).init(this.response),typeof this.config.imports=="function"&&(this.injectAppIdScript(o),await this.config.imports());const s=await we(()=>import("./FlowClient-e0ae8105.js"),[],import.meta.url);if(await this.flowInitClient(s),!e){const l=`flow-container-${o.toLowerCase()}`;this.container=document.createElement(l),te.$[o]=this.container,this.container.id=o}this.loadingFinished()}return this.container&&!this.container.isConnected&&(this.container.style.display="none",document.body.appendChild(this.container)),this.response}async loadScript(e){return new Promise((t,n)=>{const o=document.createElement("script");o.onload=()=>t(),o.onerror=n,o.src=e,document.body.appendChild(o)})}injectAppIdScript(e){const t=e.substring(0,e.lastIndexOf("-")),n=document.createElement("script");n.type="module",n.setAttribute("data-app-id",t),document.body.append(n)}async flowInitClient(e){return e.init(),new Promise(t=>{const n=setInterval(()=>{Object.keys(v.Vaadin.Flow.clients).filter(r=>r!=="TypeScript").reduce((r,s)=>r||v.Vaadin.Flow.clients[s].isActive(),!1)||(clearInterval(n),t())},5)})}async flowInitUi(e){const t=v.Vaadin&&v.Vaadin.TypeScript&&v.Vaadin.TypeScript.initial;return t?(v.Vaadin.TypeScript.initial=void 0,Promise.resolve(t)):new Promise((n,o)=>{const s=new XMLHttpRequest,l=e?"&serverSideRouting":"",a=`?v-r=init&location=${encodeURIComponent(this.getFlowRoutePath(location))}&query=${encodeURIComponent(this.getFlowRouteQuery(location))}${l}`;s.open("GET",a),s.onerror=()=>o(new Ht(`Invalid server response when initializing Flow UI.
        ${s.status}
        ${s.responseText}`)),s.onload=()=>{const d=s.getResponseHeader("content-type");d&&d.indexOf("application/json")!==-1?n(JSON.parse(s.responseText)):s.onerror()},s.send()})}addConnectionIndicator(){w.create(),v.addEventListener("online",()=>{if(!this.isFlowClientLoaded()){v.Vaadin.connectionState.state=b.RECONNECTING;const e=new XMLHttpRequest;e.open("HEAD","sw.js"),e.onload=()=>{v.Vaadin.connectionState.state=b.CONNECTED},e.onerror=()=>{v.Vaadin.connectionState.state=b.CONNECTION_LOST},setTimeout(()=>e.send(),50)}}),v.addEventListener("offline",()=>{this.isFlowClientLoaded()||(v.Vaadin.connectionState.state=b.CONNECTION_LOST)})}async offlineStubAction(){const e=document.createElement("iframe"),t="./offline-stub.html";e.setAttribute("src",t),e.setAttribute("style","width: 100%; height: 100%; border: 0"),this.response=void 0;let n;const o=()=>{n!==void 0&&(v.Vaadin.connectionState.removeStateChangeListener(n),n=void 0)};return e.onBeforeEnter=(r,s,l)=>{n=()=>{v.Vaadin.connectionState.online&&(o(),l.render(r,!1))},v.Vaadin.connectionState.addStateChangeListener(n)},e.onBeforeLeave=(r,s,l)=>{o()},e}isFlowClientLoaded(){return this.response!==void 0}}const{serverSideRoutes:Xn}=new Jn({imports:()=>we(()=>import("./generated-flow-imports-477f6ec3.js"),[],import.meta.url)}),Qn=[...Xn],Zn=new V(document.querySelector("#outlet"));Zn.setRoutes(Qn);var eo=function(){var i=document.getSelection();if(!i.rangeCount)return function(){};for(var e=document.activeElement,t=[],n=0;n<i.rangeCount;n++)t.push(i.getRangeAt(n));switch(e.tagName.toUpperCase()){case"INPUT":case"TEXTAREA":e.blur();break;default:e=null;break}return i.removeAllRanges(),function(){i.type==="Caret"&&i.removeAllRanges(),i.rangeCount||t.forEach(function(o){i.addRange(o)}),e&&e.focus()}},Wt={"text/plain":"Text","text/html":"Url",default:"Text"},to="Copy to clipboard: #{key}, Enter";function io(i){var e=(/mac os x/i.test(navigator.userAgent)?"⌘":"Ctrl")+"+C";return i.replace(/#{\s*key\s*}/g,e)}function no(i,e){var t,n,o,r,s,l,a=!1;e||(e={}),t=e.debug||!1;try{o=eo(),r=document.createRange(),s=document.getSelection(),l=document.createElement("span"),l.textContent=i,l.style.all="unset",l.style.position="fixed",l.style.top=0,l.style.clip="rect(0, 0, 0, 0)",l.style.whiteSpace="pre",l.style.webkitUserSelect="text",l.style.MozUserSelect="text",l.style.msUserSelect="text",l.style.userSelect="text",l.addEventListener("copy",function(c){if(c.stopPropagation(),e.format)if(c.preventDefault(),typeof c.clipboardData>"u"){t&&console.warn("unable to use e.clipboardData"),t&&console.warn("trying IE specific stuff"),window.clipboardData.clearData();var u=Wt[e.format]||Wt.default;window.clipboardData.setData(u,i)}else c.clipboardData.clearData(),c.clipboardData.setData(e.format,i);e.onCopy&&(c.preventDefault(),e.onCopy(c.clipboardData))}),document.body.appendChild(l),r.selectNodeContents(l),s.addRange(r);var d=document.execCommand("copy");if(!d)throw new Error("copy command was unsuccessful");a=!0}catch(c){t&&console.error("unable to copy using execCommand: ",c),t&&console.warn("trying IE specific stuff");try{window.clipboardData.setData(e.format||"text",i),e.onCopy&&e.onCopy(window.clipboardData),a=!0}catch(u){t&&console.error("unable to copy using clipboardData: ",u),t&&console.error("falling back to prompt"),n=io("message"in e?e.message:to),window.prompt(n,i)}}finally{s&&(typeof s.removeRange=="function"?s.removeRange(r):s.removeAllRanges()),l&&document.body.removeChild(l),o()}return a}const pt=1e3,ft=(i,e)=>{const t=Array.from(i.querySelectorAll(e.join(", "))),n=Array.from(i.querySelectorAll("*")).filter(o=>o.shadowRoot).flatMap(o=>ft(o.shadowRoot,e));return[...t,...n]};let jt=!1;const de=(i,e)=>{jt||(window.addEventListener("message",o=>{o.data==="validate-license"&&window.location.reload()},!1),jt=!0);const t=i._overlayElement;if(t){if(t.shadowRoot){const o=t.shadowRoot.querySelector("slot:not([name])");if(o&&o.assignedElements().length>0){de(o.assignedElements()[0],e);return}}de(t,e);return}const n=e.messageHtml?e.messageHtml:`${e.message} <p>Component: ${e.product.name} ${e.product.version}</p>`.replace(/https:([^ ]*)/g,"<a href='https:$1'>https:$1</a>");i.isConnected&&(i.outerHTML=`<no-license style="display:flex;align-items:center;text-align:center;justify-content:center;"><div>${n}</div></no-license>`)},ne={},Gt={},X={},gi={},L=i=>`${i.name}_${i.version}`,qt=i=>{const{cvdlName:e,version:t}=i.constructor,n={name:e,version:t},o=i.tagName.toLowerCase();ne[e]=ne[e]??[],ne[e].push(o);const r=X[L(n)];r&&setTimeout(()=>de(i,r),pt),X[L(n)]||gi[L(n)]||Gt[L(n)]||(Gt[L(n)]=!0,window.Vaadin.devTools.checkLicense(n))},oo=i=>{gi[L(i)]=!0,console.debug("License check ok for",i)},vi=i=>{const e=i.product.name;X[L(i.product)]=i,console.error("License check failed for",e);const t=ne[e];(t==null?void 0:t.length)>0&&ft(document,t).forEach(n=>{setTimeout(()=>de(n,X[L(i.product)]),pt)})},ro=i=>{const e=i.message,t=i.product.name;i.messageHtml=`No license found. <a target=_blank onclick="javascript:window.open(this.href);return false;" href="${e}">Go here to start a trial or retrieve your license.</a>`,X[L(i.product)]=i,console.error("No license found when checking",t);const n=ne[t];(n==null?void 0:n.length)>0&&ft(document,n).forEach(o=>{setTimeout(()=>de(o,X[L(i.product)]),pt)})},so=()=>{window.Vaadin.devTools.createdCvdlElements.forEach(i=>{qt(i)}),window.Vaadin.devTools.createdCvdlElements={push:i=>{qt(i)}}};var ao=Object.defineProperty,lo=Object.getOwnPropertyDescriptor,S=(i,e,t,n)=>{for(var o=n>1?void 0:n?lo(e,t):e,r=i.length-1,s;r>=0;r--)(s=i[r])&&(o=(n?s(e,t,o):s(o))||o);return n&&o&&ao(e,t,o),o};const _i=class extends Object{constructor(i){super(),this.status="unavailable",i&&(this.webSocket=new WebSocket(i),this.webSocket.onmessage=e=>this.handleMessage(e),this.webSocket.onerror=e=>this.handleError(e),this.webSocket.onclose=e=>{this.status!=="error"&&this.setStatus("unavailable"),this.webSocket=void 0}),setInterval(()=>{this.webSocket&&self.status!=="error"&&this.status!=="unavailable"&&this.webSocket.send("")},_i.HEARTBEAT_INTERVAL)}onHandshake(){}onReload(){}onConnectionError(i){}onStatusChange(i){}onMessage(i){console.error("Unknown message received from the live reload server:",i)}handleMessage(i){let e;try{e=JSON.parse(i.data)}catch(t){this.handleError(`[${t.name}: ${t.message}`);return}e.command==="hello"?(this.setStatus("active"),this.onHandshake()):e.command==="reload"?this.status==="active"&&this.onReload():e.command==="license-check-ok"?oo(e.data):e.command==="license-check-failed"?vi(e.data):e.command==="license-check-nokey"?ro(e.data):this.onMessage(e)}handleError(i){console.error(i),this.setStatus("error"),i instanceof Event&&this.webSocket?this.onConnectionError(`Error in WebSocket connection to ${this.webSocket.url}`):this.onConnectionError(i)}setActive(i){!i&&this.status==="active"?this.setStatus("inactive"):i&&this.status==="inactive"&&this.setStatus("active")}setStatus(i){this.status!==i&&(this.status=i,this.onStatusChange(i))}send(i,e){const t=JSON.stringify({command:i,data:e});this.webSocket?this.webSocket.readyState!==WebSocket.OPEN?this.webSocket.addEventListener("open",()=>this.webSocket.send(t)):this.webSocket.send(t):console.error(`Unable to send message ${i}. No websocket is available`)}setFeature(i,e){this.send("setFeature",{featureId:i,enabled:e})}sendTelemetry(i){this.send("reportTelemetry",{browserData:i})}sendLicenseCheck(i){this.send("checkLicense",i)}sendShowComponentCreateLocation(i){this.send("showComponentCreateLocation",i)}sendShowComponentAttachLocation(i){this.send("showComponentAttachLocation",i)}};let be=_i;be.HEARTBEAT_INTERVAL=18e4;const co=x`
  .popup {
    width: auto;
    position: fixed;
    background-color: var(--dev-tools-background-color-active-blurred);
    color: var(--dev-tools-text-color-primary);
    padding: 0.1875rem 0.75rem 0.1875rem 1rem;
    background-clip: padding-box;
    border-radius: var(--dev-tools-border-radius);
    overflow: hidden;
    margin: 0.5rem;
    width: 30rem;
    max-width: calc(100% - 1rem);
    max-height: calc(100vh - 1rem);
    flex-shrink: 1;
    background-color: var(--dev-tools-background-color-active);
    color: var(--dev-tools-text-color);
    transition: var(--dev-tools-transition-duration);
    transform-origin: bottom right;
    display: flex;
    flex-direction: column;
    box-shadow: var(--dev-tools-box-shadow);
    outline: none;
  }
`,g=class extends H{constructor(){super(),this.expanded=!1,this.messages=[],this.notifications=[],this.frontendStatus="unavailable",this.javaStatus="unavailable",this.tabs=[{id:"log",title:"Log",render:this.renderLog,activate:this.activateLog},{id:"info",title:"Info",render:this.renderInfo},{id:"features",title:"Feature Flags",render:this.renderFeatures}],this.activeTab="log",this.serverInfo={flowVersion:"",vaadinVersion:"",javaVersion:"",osVersion:"",productName:""},this.features=[],this.unreadErrors=!1,this.componentPickActive=!1,this.nextMessageId=1,this.transitionDuration=0,window.Vaadin.Flow&&this.tabs.push({id:"code",title:"Code",render:this.renderCode})}static get styles(){return[x`
        :host {
          --dev-tools-font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen-Sans, Ubuntu, Cantarell,
            'Helvetica Neue', sans-serif;
          --dev-tools-font-family-monospace: SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New',
            monospace;

          --dev-tools-font-size: 0.8125rem;
          --dev-tools-font-size-small: 0.75rem;

          --dev-tools-text-color: rgba(255, 255, 255, 0.8);
          --dev-tools-text-color-secondary: rgba(255, 255, 255, 0.65);
          --dev-tools-text-color-emphasis: rgba(255, 255, 255, 0.95);
          --dev-tools-text-color-active: rgba(255, 255, 255, 1);

          --dev-tools-background-color-inactive: rgba(45, 45, 45, 0.25);
          --dev-tools-background-color-active: rgba(45, 45, 45, 0.98);
          --dev-tools-background-color-active-blurred: rgba(45, 45, 45, 0.85);

          --dev-tools-border-radius: 0.5rem;
          --dev-tools-box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.05), 0 4px 12px -2px rgba(0, 0, 0, 0.4);

          --dev-tools-blue-hsl: ${this.BLUE_HSL};
          --dev-tools-blue-color: hsl(var(--dev-tools-blue-hsl));
          --dev-tools-green-hsl: ${this.GREEN_HSL};
          --dev-tools-green-color: hsl(var(--dev-tools-green-hsl));
          --dev-tools-grey-hsl: ${this.GREY_HSL};
          --dev-tools-grey-color: hsl(var(--dev-tools-grey-hsl));
          --dev-tools-yellow-hsl: ${this.YELLOW_HSL};
          --dev-tools-yellow-color: hsl(var(--dev-tools-yellow-hsl));
          --dev-tools-red-hsl: ${this.RED_HSL};
          --dev-tools-red-color: hsl(var(--dev-tools-red-hsl));

          /* Needs to be in ms, used in JavaScript as well */
          --dev-tools-transition-duration: 180ms;

          all: initial;

          direction: ltr;
          cursor: default;
          font: normal 400 var(--dev-tools-font-size) / 1.125rem var(--dev-tools-font-family);
          color: var(--dev-tools-text-color);
          -webkit-user-select: none;
          -moz-user-select: none;
          user-select: none;

          position: fixed;
          z-index: 20000;
          pointer-events: none;
          bottom: 0;
          right: 0;
          width: 100%;
          height: 100%;
          display: flex;
          flex-direction: column-reverse;
          align-items: flex-end;
        }

        .dev-tools {
          pointer-events: auto;
          display: flex;
          align-items: center;
          position: fixed;
          z-index: inherit;
          right: 0.5rem;
          bottom: 0.5rem;
          min-width: 1.75rem;
          height: 1.75rem;
          max-width: 1.75rem;
          border-radius: 0.5rem;
          padding: 0.375rem;
          box-sizing: border-box;
          background-color: var(--dev-tools-background-color-inactive);
          box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.05);
          color: var(--dev-tools-text-color);
          transition: var(--dev-tools-transition-duration);
          white-space: nowrap;
          line-height: 1rem;
        }

        .dev-tools:hover,
        .dev-tools.active {
          background-color: var(--dev-tools-background-color-active);
          box-shadow: var(--dev-tools-box-shadow);
        }

        .dev-tools.active {
          max-width: calc(100% - 1rem);
        }

        .dev-tools .dev-tools-icon {
          flex: none;
          pointer-events: none;
          display: inline-block;
          width: 1rem;
          height: 1rem;
          fill: #fff;
          transition: var(--dev-tools-transition-duration);
          margin: 0;
        }

        .dev-tools.active .dev-tools-icon {
          opacity: 0;
          position: absolute;
          transform: scale(0.5);
        }

        .dev-tools .status-blip {
          flex: none;
          display: block;
          width: 6px;
          height: 6px;
          border-radius: 50%;
          z-index: 20001;
          background: var(--dev-tools-grey-color);
          position: absolute;
          top: -1px;
          right: -1px;
        }

        .dev-tools .status-description {
          overflow: hidden;
          text-overflow: ellipsis;
          padding: 0 0.25rem;
        }

        .dev-tools.error {
          background-color: hsla(var(--dev-tools-red-hsl), 0.15);
          animation: bounce 0.5s;
          animation-iteration-count: 2;
        }

        .switch {
          display: inline-flex;
          align-items: center;
        }

        .switch input {
          opacity: 0;
          width: 0;
          height: 0;
          position: absolute;
        }

        .switch .slider {
          display: block;
          flex: none;
          width: 28px;
          height: 18px;
          border-radius: 9px;
          background-color: rgba(255, 255, 255, 0.3);
          transition: var(--dev-tools-transition-duration);
          margin-right: 0.5rem;
        }

        .switch:focus-within .slider,
        .switch .slider:hover {
          background-color: rgba(255, 255, 255, 0.35);
          transition: none;
        }

        .switch input:focus-visible ~ .slider {
          box-shadow: 0 0 0 2px var(--dev-tools-background-color-active), 0 0 0 4px var(--dev-tools-blue-color);
        }

        .switch .slider::before {
          content: '';
          display: block;
          margin: 2px;
          width: 14px;
          height: 14px;
          background-color: #fff;
          transition: var(--dev-tools-transition-duration);
          border-radius: 50%;
        }

        .switch input:checked + .slider {
          background-color: var(--dev-tools-green-color);
        }

        .switch input:checked + .slider::before {
          transform: translateX(10px);
        }

        .switch input:disabled + .slider::before {
          background-color: var(--dev-tools-grey-color);
        }

        .window.hidden {
          opacity: 0;
          transform: scale(0);
          position: absolute;
        }

        .window.visible {
          transform: none;
          opacity: 1;
          pointer-events: auto;
        }

        .window.visible ~ .dev-tools {
          opacity: 0;
          pointer-events: none;
        }

        .window.visible ~ .dev-tools .dev-tools-icon,
        .window.visible ~ .dev-tools .status-blip {
          transition: none;
          opacity: 0;
        }

        .window {
          border-radius: var(--dev-tools-border-radius);
          overflow: hidden;
          margin: 0.5rem;
          width: 30rem;
          max-width: calc(100% - 1rem);
          max-height: calc(100vh - 1rem);
          flex-shrink: 1;
          background-color: var(--dev-tools-background-color-active);
          color: var(--dev-tools-text-color);
          transition: var(--dev-tools-transition-duration);
          transform-origin: bottom right;
          display: flex;
          flex-direction: column;
          box-shadow: var(--dev-tools-box-shadow);
          outline: none;
        }

        .window-toolbar {
          display: flex;
          flex: none;
          align-items: center;
          padding: 0.375rem;
          white-space: nowrap;
          order: 1;
          background-color: rgba(0, 0, 0, 0.2);
          gap: 0.5rem;
        }

        .tab {
          color: var(--dev-tools-text-color-secondary);
          font: inherit;
          font-size: var(--dev-tools-font-size-small);
          font-weight: 500;
          line-height: 1;
          padding: 0.25rem 0.375rem;
          background: none;
          border: none;
          margin: 0;
          border-radius: 0.25rem;
          transition: var(--dev-tools-transition-duration);
        }

        .tab:hover,
        .tab.active {
          color: var(--dev-tools-text-color-active);
        }

        .tab.active {
          background-color: rgba(255, 255, 255, 0.12);
        }

        .tab.unreadErrors::after {
          content: '•';
          color: hsl(var(--dev-tools-red-hsl));
          font-size: 1.5rem;
          position: absolute;
          transform: translate(0, -50%);
        }

        .ahreflike {
          font-weight: 500;
          color: var(--dev-tools-text-color-secondary);
          text-decoration: underline;
          cursor: pointer;
        }

        .ahreflike:hover {
          color: var(--dev-tools-text-color-emphasis);
        }

        .button {
          all: initial;
          font-family: inherit;
          font-size: var(--dev-tools-font-size-small);
          line-height: 1;
          white-space: nowrap;
          background-color: rgba(0, 0, 0, 0.2);
          color: inherit;
          font-weight: 600;
          padding: 0.25rem 0.375rem;
          border-radius: 0.25rem;
        }

        .button:focus,
        .button:hover {
          color: var(--dev-tools-text-color-emphasis);
        }

        .minimize-button {
          flex: none;
          width: 1rem;
          height: 1rem;
          color: inherit;
          background-color: transparent;
          border: 0;
          padding: 0;
          margin: 0 0 0 auto;
          opacity: 0.8;
        }

        .minimize-button:hover {
          opacity: 1;
        }

        .minimize-button svg {
          max-width: 100%;
        }

        .message.information {
          --dev-tools-notification-color: var(--dev-tools-blue-color);
        }

        .message.warning {
          --dev-tools-notification-color: var(--dev-tools-yellow-color);
        }

        .message.error {
          --dev-tools-notification-color: var(--dev-tools-red-color);
        }

        .message {
          display: flex;
          padding: 0.1875rem 0.75rem 0.1875rem 2rem;
          background-clip: padding-box;
        }

        .message.log {
          padding-left: 0.75rem;
        }

        .message-content {
          margin-right: 0.5rem;
          -webkit-user-select: text;
          -moz-user-select: text;
          user-select: text;
        }

        .message-heading {
          position: relative;
          display: flex;
          align-items: center;
          margin: 0.125rem 0;
        }

        .message.log {
          color: var(--dev-tools-text-color-secondary);
        }

        .message:not(.log) .message-heading {
          font-weight: 500;
        }

        .message.has-details .message-heading {
          color: var(--dev-tools-text-color-emphasis);
          font-weight: 600;
        }

        .message-heading::before {
          position: absolute;
          margin-left: -1.5rem;
          display: inline-block;
          text-align: center;
          font-size: 0.875em;
          font-weight: 600;
          line-height: calc(1.25em - 2px);
          width: 14px;
          height: 14px;
          box-sizing: border-box;
          border: 1px solid transparent;
          border-radius: 50%;
        }

        .message.information .message-heading::before {
          content: 'i';
          border-color: currentColor;
          color: var(--dev-tools-notification-color);
        }

        .message.warning .message-heading::before,
        .message.error .message-heading::before {
          content: '!';
          color: var(--dev-tools-background-color-active);
          background-color: var(--dev-tools-notification-color);
        }

        .features-tray {
          padding: 0.75rem;
          flex: auto;
          overflow: auto;
          animation: fade-in var(--dev-tools-transition-duration) ease-in;
          user-select: text;
        }

        .features-tray p {
          margin-top: 0;
          color: var(--dev-tools-text-color-secondary);
        }

        .features-tray .feature {
          display: flex;
          align-items: center;
          gap: 1rem;
          padding-bottom: 0.5em;
        }

        .message .message-details {
          font-weight: 400;
          color: var(--dev-tools-text-color-secondary);
          margin: 0.25rem 0;
        }

        .message .message-details[hidden] {
          display: none;
        }

        .message .message-details p {
          display: inline;
          margin: 0;
          margin-right: 0.375em;
          word-break: break-word;
        }

        .message .persist {
          color: var(--dev-tools-text-color-secondary);
          white-space: nowrap;
          margin: 0.375rem 0;
          display: flex;
          align-items: center;
          position: relative;
          -webkit-user-select: none;
          -moz-user-select: none;
          user-select: none;
        }

        .message .persist::before {
          content: '';
          width: 1em;
          height: 1em;
          border-radius: 0.2em;
          margin-right: 0.375em;
          background-color: rgba(255, 255, 255, 0.3);
        }

        .message .persist:hover::before {
          background-color: rgba(255, 255, 255, 0.4);
        }

        .message .persist.on::before {
          background-color: rgba(255, 255, 255, 0.9);
        }

        .message .persist.on::after {
          content: '';
          order: -1;
          position: absolute;
          width: 0.75em;
          height: 0.25em;
          border: 2px solid var(--dev-tools-background-color-active);
          border-width: 0 0 2px 2px;
          transform: translate(0.05em, -0.05em) rotate(-45deg) scale(0.8, 0.9);
        }

        .message .dismiss-message {
          font-weight: 600;
          align-self: stretch;
          display: flex;
          align-items: center;
          padding: 0 0.25rem;
          margin-left: 0.5rem;
          color: var(--dev-tools-text-color-secondary);
        }

        .message .dismiss-message:hover {
          color: var(--dev-tools-text-color);
        }

        .notification-tray {
          display: flex;
          flex-direction: column-reverse;
          align-items: flex-end;
          margin: 0.5rem;
          flex: none;
        }

        .window.hidden + .notification-tray {
          margin-bottom: 3rem;
        }

        .notification-tray .message {
          pointer-events: auto;
          background-color: var(--dev-tools-background-color-active);
          color: var(--dev-tools-text-color);
          max-width: 30rem;
          box-sizing: border-box;
          border-radius: var(--dev-tools-border-radius);
          margin-top: 0.5rem;
          transition: var(--dev-tools-transition-duration);
          transform-origin: bottom right;
          animation: slideIn var(--dev-tools-transition-duration);
          box-shadow: var(--dev-tools-box-shadow);
          padding-top: 0.25rem;
          padding-bottom: 0.25rem;
        }

        .notification-tray .message.animate-out {
          animation: slideOut forwards var(--dev-tools-transition-duration);
        }

        .notification-tray .message .message-details {
          max-height: 10em;
          overflow: hidden;
        }

        .message-tray {
          flex: auto;
          overflow: auto;
          max-height: 20rem;
          user-select: text;
        }

        .message-tray .message {
          animation: fade-in var(--dev-tools-transition-duration) ease-in;
          padding-left: 2.25rem;
        }

        .message-tray .message.warning {
          background-color: hsla(var(--dev-tools-yellow-hsl), 0.09);
        }

        .message-tray .message.error {
          background-color: hsla(var(--dev-tools-red-hsl), 0.09);
        }

        .message-tray .message.error .message-heading {
          color: hsl(var(--dev-tools-red-hsl));
        }

        .message-tray .message.warning .message-heading {
          color: hsl(var(--dev-tools-yellow-hsl));
        }

        .message-tray .message + .message {
          border-top: 1px solid rgba(255, 255, 255, 0.07);
        }

        .message-tray .dismiss-message,
        .message-tray .persist {
          display: none;
        }

        .info-tray {
          padding: 0.75rem;
          position: relative;
          flex: auto;
          overflow: auto;
          animation: fade-in var(--dev-tools-transition-duration) ease-in;
          user-select: text;
        }

        .info-tray dl {
          margin: 0;
          display: grid;
          grid-template-columns: max-content 1fr;
          column-gap: 0.75rem;
          position: relative;
        }

        .info-tray dt {
          grid-column: 1;
          color: var(--dev-tools-text-color-emphasis);
        }

        .info-tray dt:not(:first-child)::before {
          content: '';
          width: 100%;
          position: absolute;
          height: 1px;
          background-color: rgba(255, 255, 255, 0.1);
          margin-top: -0.375rem;
        }

        .info-tray dd {
          grid-column: 2;
          margin: 0;
        }

        .info-tray :is(dt, dd):not(:last-child) {
          margin-bottom: 0.75rem;
        }

        .info-tray dd + dd {
          margin-top: -0.5rem;
        }

        .info-tray .live-reload-status::before {
          content: '•';
          color: var(--status-color);
          width: 0.75rem;
          display: inline-block;
          font-size: 1rem;
          line-height: 0.5rem;
        }

        .info-tray .copy {
          position: fixed;
          z-index: 1;
          top: 0.5rem;
          right: 0.5rem;
        }

        .info-tray .switch {
          vertical-align: -4px;
        }

        @keyframes slideIn {
          from {
            transform: translateX(100%);
            opacity: 0;
          }
          to {
            transform: translateX(0%);
            opacity: 1;
          }
        }

        @keyframes slideOut {
          from {
            transform: translateX(0%);
            opacity: 1;
          }
          to {
            transform: translateX(100%);
            opacity: 0;
          }
        }

        @keyframes fade-in {
          0% {
            opacity: 0;
          }
        }

        @keyframes bounce {
          0% {
            transform: scale(0.8);
          }
          50% {
            transform: scale(1.5);
            background-color: hsla(var(--dev-tools-red-hsl), 1);
          }
          100% {
            transform: scale(1);
          }
        }

        @supports (backdrop-filter: blur(1px)) {
          .dev-tools,
          .window,
          .notification-tray .message {
            backdrop-filter: blur(8px);
          }
          .dev-tools:hover,
          .dev-tools.active,
          .window,
          .notification-tray .message {
            background-color: var(--dev-tools-background-color-active-blurred);
          }
        }
      `,co]}static get isActive(){const i=window.sessionStorage.getItem(g.ACTIVE_KEY_IN_SESSION_STORAGE);return i===null||i!=="false"}static notificationDismissed(i){const e=window.localStorage.getItem(g.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE);return e!==null&&e.includes(i)}elementTelemetry(){let i={};try{const e=localStorage.getItem("vaadin.statistics.basket");if(!e)return;i=JSON.parse(e)}catch{return}this.frontendConnection&&this.frontendConnection.sendTelemetry(i)}openWebSocketConnection(){this.frontendStatus="unavailable",this.javaStatus="unavailable";const i=s=>this.log("error",s),e=()=>{if(this.liveReloadDisabled)return;this.showSplashMessage("Reloading…");const s=window.sessionStorage.getItem(g.TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE),l=s?parseInt(s,10)+1:1;window.sessionStorage.setItem(g.TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE,l.toString()),window.sessionStorage.setItem(g.TRIGGERED_KEY_IN_SESSION_STORAGE,"true"),window.location.reload()},t=new be(this.getDedicatedWebSocketUrl());t.onHandshake=()=>{this.log("log","Vaadin development mode initialized"),g.isActive||t.setActive(!1),this.elementTelemetry()},t.onConnectionError=i,t.onReload=e,t.onStatusChange=s=>{this.frontendStatus=s},t.onMessage=s=>{(s==null?void 0:s.command)==="serverInfo"?this.serverInfo=s.data:(s==null?void 0:s.command)==="featureFlags"?this.features=s.data.features:console.error("Unknown message from front-end connection:",JSON.stringify(s))},this.frontendConnection=t;let n;this.backend===g.SPRING_BOOT_DEVTOOLS&&this.springBootLiveReloadPort?(n=new be(this.getSpringBootWebSocketUrl(window.location)),n.onHandshake=()=>{g.isActive||n.setActive(!1)},n.onReload=e,n.onConnectionError=i):this.backend===g.JREBEL||this.backend===g.HOTSWAP_AGENT?n=t:n=new be(void 0);const o=n.onStatusChange;n.onStatusChange=s=>{o(s),this.javaStatus=s};const r=n.onHandshake;n.onHandshake=()=>{r(),this.backend&&this.log("information",`Java live reload available: ${g.BACKEND_DISPLAY_NAME[this.backend]}`)},this.javaConnection=n,this.backend||this.showNotification("warning","Java live reload unavailable","Live reload for Java changes is currently not set up. Find out how to make use of this functionality to boost your workflow.","https://vaadin.com/docs/latest/flow/configuration/live-reload","liveReloadUnavailable")}getDedicatedWebSocketUrl(){function i(t){const n=document.createElement("div");return n.innerHTML=`<a href="${t}"/>`,n.firstChild.href}if(this.url===void 0)return;const e=i(this.url);if(!e.startsWith("http://")&&!e.startsWith("https://")){console.error("The protocol of the url should be http or https for live reload to work.");return}return`${e.replace(/^http/,"ws")}?v-r=push&debug_window`}getSpringBootWebSocketUrl(i){const{hostname:e}=i,t=i.protocol==="https:"?"wss":"ws";if(e.endsWith("gitpod.io")){const n=e.replace(/.*?-/,"");return`${t}://${this.springBootLiveReloadPort}-${n}`}else return`${t}://${e}:${this.springBootLiveReloadPort}`}connectedCallback(){if(super.connectedCallback(),this.catchErrors(),this.disableEventListener=t=>this.demoteSplashMessage(),document.body.addEventListener("focus",this.disableEventListener),document.body.addEventListener("click",this.disableEventListener),this.openWebSocketConnection(),window.sessionStorage.getItem(g.TRIGGERED_KEY_IN_SESSION_STORAGE)){const t=new Date,n=`${`0${t.getHours()}`.slice(-2)}:${`0${t.getMinutes()}`.slice(-2)}:${`0${t.getSeconds()}`.slice(-2)}`;this.showSplashMessage(`Page reloaded at ${n}`),window.sessionStorage.removeItem(g.TRIGGERED_KEY_IN_SESSION_STORAGE)}this.transitionDuration=parseInt(window.getComputedStyle(this).getPropertyValue("--dev-tools-transition-duration"),10);const e=window;e.Vaadin=e.Vaadin||{},e.Vaadin.devTools=Object.assign(this,e.Vaadin.devTools),so(),document.documentElement.addEventListener("vaadin-overlay-outside-click",t=>{t.detail.sourceEvent.composedPath().includes(this)&&t.preventDefault()})}format(i){return i.toString()}catchErrors(){const i=window.Vaadin.ConsoleErrors;i&&i.forEach(e=>{this.log("error",e.map(t=>this.format(t)).join(" "))}),window.Vaadin.ConsoleErrors={push:e=>{this.log("error",e.map(t=>this.format(t)).join(" "))}}}disconnectedCallback(){this.disableEventListener&&(document.body.removeEventListener("focus",this.disableEventListener),document.body.removeEventListener("click",this.disableEventListener)),super.disconnectedCallback()}toggleExpanded(){this.notifications.slice().forEach(i=>this.dismissNotification(i.id)),this.expanded=!this.expanded,this.expanded&&this.root.focus()}showSplashMessage(i){this.splashMessage=i,this.splashMessage&&(this.expanded?this.demoteSplashMessage():setTimeout(()=>{this.demoteSplashMessage()},g.AUTO_DEMOTE_NOTIFICATION_DELAY))}demoteSplashMessage(){this.splashMessage&&this.log("log",this.splashMessage),this.showSplashMessage(void 0)}checkLicense(i){this.frontendConnection?this.frontendConnection.sendLicenseCheck(i):vi({message:"Internal error: no connection",product:i})}log(i,e,t,n){const o=this.nextMessageId;for(this.nextMessageId+=1,this.messages.push({id:o,type:i,message:e,details:t,link:n,dontShowAgain:!1,deleted:!1});this.messages.length>g.MAX_LOG_ROWS;)this.messages.shift();this.requestUpdate(),this.updateComplete.then(()=>{const r=this.renderRoot.querySelector(".message-tray .message:last-child");this.expanded&&r?(setTimeout(()=>r.scrollIntoView({behavior:"smooth"}),this.transitionDuration),this.unreadErrors=!1):i==="error"&&(this.unreadErrors=!0)})}showNotification(i,e,t,n,o){if(o===void 0||!g.notificationDismissed(o)){if(this.notifications.filter(l=>l.persistentId===o).filter(l=>!l.deleted).length>0)return;const s=this.nextMessageId;this.nextMessageId+=1,this.notifications.push({id:s,type:i,message:e,details:t,link:n,persistentId:o,dontShowAgain:!1,deleted:!1}),n===void 0&&setTimeout(()=>{this.dismissNotification(s)},g.AUTO_DEMOTE_NOTIFICATION_DELAY),this.requestUpdate()}else this.log(i,e,t,n)}dismissNotification(i){const e=this.findNotificationIndex(i);if(e!==-1&&!this.notifications[e].deleted){const t=this.notifications[e];if(t.dontShowAgain&&t.persistentId&&!g.notificationDismissed(t.persistentId)){let n=window.localStorage.getItem(g.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE);n=n===null?t.persistentId:`${n},${t.persistentId}`,window.localStorage.setItem(g.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE,n)}t.deleted=!0,this.log(t.type,t.message,t.details,t.link),setTimeout(()=>{const n=this.findNotificationIndex(i);n!==-1&&(this.notifications.splice(n,1),this.requestUpdate())},this.transitionDuration)}}findNotificationIndex(i){let e=-1;return this.notifications.some((t,n)=>t.id===i?(e=n,!0):!1),e}toggleDontShowAgain(i){const e=this.findNotificationIndex(i);if(e!==-1&&!this.notifications[e].deleted){const t=this.notifications[e];t.dontShowAgain=!t.dontShowAgain,this.requestUpdate()}}setActive(i){var e,t;(e=this.frontendConnection)==null||e.setActive(i),(t=this.javaConnection)==null||t.setActive(i),window.sessionStorage.setItem(g.ACTIVE_KEY_IN_SESSION_STORAGE,i?"true":"false")}getStatusColor(i){return i==="active"?x`hsl(${g.GREEN_HSL})`:i==="inactive"?x`hsl(${g.GREY_HSL})`:i==="unavailable"?x`hsl(${g.YELLOW_HSL})`:i==="error"?x`hsl(${g.RED_HSL})`:x`none`}renderMessage(i){return E`
      <div
        class="message ${i.type} ${i.deleted?"animate-out":""} ${i.details||i.link?"has-details":""}"
      >
        <div class="message-content">
          <div class="message-heading">${i.message}</div>
          <div class="message-details" ?hidden="${!i.details&&!i.link}">
            ${i.details?E`<p>${i.details}</p>`:""}
            ${i.link?E`<a class="ahreflike" href="${i.link}" target="_blank">Learn more</a>`:""}
          </div>
          ${i.persistentId?E`<div
                class="persist ${i.dontShowAgain?"on":"off"}"
                @click=${()=>this.toggleDontShowAgain(i.id)}
              >
                Don’t show again
              </div>`:""}
        </div>
        <div class="dismiss-message" @click=${()=>this.dismissNotification(i.id)}>Dismiss</div>
      </div>
    `}render(){return E` <div
        class="window ${this.expanded&&!this.componentPickActive?"visible":"hidden"}"
        tabindex="0"
        @keydown=${i=>i.key==="Escape"&&this.expanded&&this.toggleExpanded()}
      >
        <div class="window-toolbar">
          ${this.tabs.map(i=>E`<button
                class=${mi({tab:!0,active:this.activeTab===i.id,unreadErrors:i.id==="log"&&this.unreadErrors})}
                id="${i.id}"
                @click=${()=>{this.activeTab=i.id,i.activate&&i.activate.call(this)}}
              >
                ${i.title}
              </button> `)}
          <button class="minimize-button" title="Minimize" @click=${()=>this.toggleExpanded()}>
            <svg fill="none" height="16" viewBox="0 0 16 16" width="16" xmlns="http://www.w3.org/2000/svg">
              <g fill="#fff" opacity=".8">
                <path
                  d="m7.25 1.75c0-.41421.33579-.75.75-.75h3.25c2.0711 0 3.75 1.67893 3.75 3.75v6.5c0 2.0711-1.6789 3.75-3.75 3.75h-6.5c-2.07107 0-3.75-1.6789-3.75-3.75v-3.25c0-.41421.33579-.75.75-.75s.75.33579.75.75v3.25c0 1.2426 1.00736 2.25 2.25 2.25h6.5c1.2426 0 2.25-1.0074 2.25-2.25v-6.5c0-1.24264-1.0074-2.25-2.25-2.25h-3.25c-.41421 0-.75-.33579-.75-.75z"
                />
                <path
                  d="m2.96967 2.96967c.29289-.29289.76777-.29289 1.06066 0l5.46967 5.46967v-2.68934c0-.41421.33579-.75.75-.75.4142 0 .75.33579.75.75v4.5c0 .4142-.3358.75-.75.75h-4.5c-.41421 0-.75-.3358-.75-.75 0-.41421.33579-.75.75-.75h2.68934l-5.46967-5.46967c-.29289-.29289-.29289-.76777 0-1.06066z"
                />
              </g>
            </svg>
          </button>
        </div>
        ${this.tabs.map(i=>this.activeTab===i.id?i.render.call(this):y)}
      </div>

      <div class="notification-tray">${this.notifications.map(i=>this.renderMessage(i))}</div>
      <vaadin-dev-tools-component-picker
        .active=${this.componentPickActive}
        @component-picker-pick=${i=>{const e=i.detail.component;this.renderRoot.querySelector("#locationType").value==="create"?this.frontendConnection.sendShowComponentCreateLocation(e):this.frontendConnection.sendShowComponentAttachLocation(e),this.componentPickActive=!1}}
        @component-picker-abort=${i=>{this.componentPickActive=!1}}
      ></vaadin-dev-tools-component-picker>
      <div
        class="dev-tools ${this.splashMessage?"active":""}${this.unreadErrors?" error":""}"
        @click=${()=>this.toggleExpanded()}
      >
        ${this.unreadErrors?E`<svg
              fill="none"
              height="16"
              viewBox="0 0 16 16"
              width="16"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              class="dev-tools-icon error"
            >
              <clipPath id="a"><path d="m0 0h16v16h-16z" /></clipPath>
              <g clip-path="url(#a)">
                <path
                  d="m6.25685 2.09894c.76461-1.359306 2.72169-1.359308 3.4863 0l5.58035 9.92056c.7499 1.3332-.2135 2.9805-1.7432 2.9805h-11.1606c-1.529658 0-2.4930857-1.6473-1.743156-2.9805z"
                  fill="#ff5c69"
                />
                <path
                  d="m7.99699 4c-.45693 0-.82368.37726-.81077.834l.09533 3.37352c.01094.38726.32803.69551.71544.69551.38741 0 .70449-.30825.71544-.69551l.09533-3.37352c.0129-.45674-.35384-.834-.81077-.834zm.00301 8c.60843 0 1-.3879 1-.979 0-.5972-.39157-.9851-1-.9851s-1 .3879-1 .9851c0 .5911.39157.979 1 .979z"
                  fill="#fff"
                />
              </g>
            </svg>`:E`<svg
              fill="none"
              height="17"
              viewBox="0 0 16 17"
              width="16"
              xmlns="http://www.w3.org/2000/svg"
              class="dev-tools-icon logo"
            >
              <g fill="#fff">
                <path
                  d="m8.88273 5.97926c0 .04401-.0032.08898-.00801.12913-.02467.42848-.37813.76767-.8117.76767-.43358 0-.78704-.34112-.81171-.76928-.00481-.04015-.00801-.08351-.00801-.12752 0-.42784-.10255-.87656-1.14434-.87656h-3.48364c-1.57118 0-2.315271-.72849-2.315271-2.21758v-1.26683c0-.42431.324618-.768314.748261-.768314.42331 0 .74441.344004.74441.768314v.42784c0 .47924.39576.81265 1.11293.81265h3.41538c1.5542 0 1.67373 1.156 1.725 1.7679h.03429c.05095-.6119.17048-1.7679 1.72468-1.7679h3.4154c.7172 0 1.0145-.32924 1.0145-.80847l-.0067-.43202c0-.42431.3227-.768314.7463-.768314.4234 0 .7255.344004.7255.768314v1.26683c0 1.48909-.6181 2.21758-2.1893 2.21758h-3.4836c-1.04182 0-1.14437.44872-1.14437.87656z"
                />
                <path
                  d="m8.82577 15.1648c-.14311.3144-.4588.5335-.82635.5335-.37268 0-.69252-.2249-.83244-.5466-.00206-.0037-.00412-.0073-.00617-.0108-.00275-.0047-.00549-.0094-.00824-.0145l-3.16998-5.87318c-.08773-.15366-.13383-.32816-.13383-.50395 0-.56168.45592-1.01879 1.01621-1.01879.45048 0 .75656.22069.96595.6993l2.16882 4.05042 2.17166-4.05524c.2069-.47379.513-.69448.9634-.69448.5603 0 1.0166.45711 1.0166 1.01879 0 .17579-.0465.35029-.1348.50523l-3.1697 5.8725c-.00503.0096-.01006.0184-.01509.0272-.00201.0036-.00402.0071-.00604.0106z"
                />
              </g>
            </svg>`}

        <span
          class="status-blip"
          style="background: linear-gradient(to right, ${this.getStatusColor(this.frontendStatus)} 50%, ${this.getStatusColor(this.javaStatus)} 50%)"
        ></span>
        ${this.splashMessage?E`<span class="status-description">${this.splashMessage}</span></div>`:y}
      </div>`}renderLog(){return E`<div class="message-tray">${this.messages.map(i=>this.renderMessage(i))}</div>`}activateLog(){this.unreadErrors=!1,this.updateComplete.then(()=>{const i=this.renderRoot.querySelector(".message-tray .message:last-child");i&&i.scrollIntoView()})}renderCode(){return E`<div class="info-tray">
      <div>
        <select id="locationType">
          <option value="create" selected>Create</option>
          <option value="attach">Attach</option>
        </select>
        <button
          class="button pick"
          @click=${()=>{this.componentPickActive=!0,we(()=>import("./component-picker-a17ca763.js"),[],import.meta.url)}}
        >
          Find component in code
        </button>
      </div>
      </div>
    </div>`}renderInfo(){return E`<div class="info-tray">
      <button class="button copy" @click=${this.copyInfoToClipboard}>Copy</button>
      <dl>
        <dt>${this.serverInfo.productName}</dt>
        <dd>${this.serverInfo.vaadinVersion}</dd>
        <dt>Flow</dt>
        <dd>${this.serverInfo.flowVersion}</dd>
        <dt>Java</dt>
        <dd>${this.serverInfo.javaVersion}</dd>
        <dt>OS</dt>
        <dd>${this.serverInfo.osVersion}</dd>
        <dt>Browser</dt>
        <dd>${navigator.userAgent}</dd>
        <dt>
          Live reload
          <label class="switch">
            <input
              id="toggle"
              type="checkbox"
              ?disabled=${this.liveReloadDisabled||(this.frontendStatus==="unavailable"||this.frontendStatus==="error")&&(this.javaStatus==="unavailable"||this.javaStatus==="error")}
              ?checked="${this.frontendStatus==="active"||this.javaStatus==="active"}"
              @change=${i=>this.setActive(i.target.checked)}
            />
            <span class="slider"></span>
          </label>
        </dt>
        <dd class="live-reload-status" style="--status-color: ${this.getStatusColor(this.javaStatus)}">
          Java ${this.javaStatus} ${this.backend?`(${g.BACKEND_DISPLAY_NAME[this.backend]})`:""}
        </dd>
        <dd class="live-reload-status" style="--status-color: ${this.getStatusColor(this.frontendStatus)}">
          Front end ${this.frontendStatus}
        </dd>
      </dl>
    </div>`}renderFeatures(){return E`<div class="features-tray">
      ${this.features.map(i=>E`<div class="feature">
          <label class="switch">
            <input
              class="feature-toggle"
              id="feature-toggle-${i.id}"
              type="checkbox"
              ?checked=${i.enabled}
              @change=${e=>this.toggleFeatureFlag(e,i)}
            />
            <span class="slider"></span>
            ${i.title}
          </label>
          <a class="ahreflike" href="${i.moreInfoLink}" target="_blank">Learn more</a>
        </div>`)}
    </div>`}copyInfoToClipboard(){const i=this.renderRoot.querySelectorAll(".info-tray dt, .info-tray dd"),e=Array.from(i).map(t=>(t.localName==="dd"?": ":`
`)+t.textContent.trim()).join("").replace(/^\n/,"");no(e),this.showNotification("information","Environment information copied to clipboard",void 0,void 0,"versionInfoCopied")}toggleFeatureFlag(i,e){const t=i.target.checked;this.frontendConnection?(this.frontendConnection.setFeature(e.id,t),this.showNotification("information",`“${e.title}” ${t?"enabled":"disabled"}`,e.requiresServerRestart?"This feature requires a server restart":void 0,void 0,`feature${e.id}${t?"Enabled":"Disabled"}`)):this.log("error",`Unable to toggle feature ${e.title}: No server connection available`)}};let m=g;m.BLUE_HSL=x`206, 100%, 70%`;m.GREEN_HSL=x`145, 80%, 42%`;m.GREY_HSL=x`0, 0%, 50%`;m.YELLOW_HSL=x`38, 98%, 64%`;m.RED_HSL=x`355, 100%, 68%`;m.MAX_LOG_ROWS=1e3;m.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE="vaadin.live-reload.dismissedNotifications";m.ACTIVE_KEY_IN_SESSION_STORAGE="vaadin.live-reload.active";m.TRIGGERED_KEY_IN_SESSION_STORAGE="vaadin.live-reload.triggered";m.TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE="vaadin.live-reload.triggeredCount";m.AUTO_DEMOTE_NOTIFICATION_DELAY=5e3;m.HOTSWAP_AGENT="HOTSWAP_AGENT";m.JREBEL="JREBEL";m.SPRING_BOOT_DEVTOOLS="SPRING_BOOT_DEVTOOLS";m.BACKEND_DISPLAY_NAME={HOTSWAP_AGENT:"HotswapAgent",JREBEL:"JRebel",SPRING_BOOT_DEVTOOLS:"Spring Boot Devtools"};S([_({type:String})],m.prototype,"url",2);S([_({type:Boolean,attribute:!0})],m.prototype,"liveReloadDisabled",2);S([_({type:String})],m.prototype,"backend",2);S([_({type:Number})],m.prototype,"springBootLiveReloadPort",2);S([_({type:Boolean,attribute:!1})],m.prototype,"expanded",2);S([_({type:Array,attribute:!1})],m.prototype,"messages",2);S([_({type:String,attribute:!1})],m.prototype,"splashMessage",2);S([_({type:Array,attribute:!1})],m.prototype,"notifications",2);S([_({type:String,attribute:!1})],m.prototype,"frontendStatus",2);S([_({type:String,attribute:!1})],m.prototype,"javaStatus",2);S([Z()],m.prototype,"tabs",2);S([Z()],m.prototype,"activeTab",2);S([Z()],m.prototype,"serverInfo",2);S([Z()],m.prototype,"features",2);S([Z()],m.prototype,"unreadErrors",2);S([Wn(".window")],m.prototype,"root",2);S([Z()],m.prototype,"componentPickActive",2);customElements.get("vaadin-dev-tools")===void 0&&customElements.define("vaadin-dev-tools",m);export{si as C,Kn as D,H as L,Gn as P,B as a,_ as b,x as c,qn as d,Z as e,E as h,y as n,co as p,Wn as q,ye as r,uo as s,fn as u};
