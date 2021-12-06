// modal login
$(window).on('load', function () {
$('#preloader .inner').fadeOut();
$('#preloader').delay(200).fadeOut('slow');
$('body').delay(200).css({'overflow': 'visible'});
})

//table
$(window).on("load resize ", function() {
var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
$('.tbl-header').css({'padding-right':scrollWidth});
}).resize();

// tooltip
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
  });

function usuarioLogado() {
  var usuarioCorrenteLocal = JSON.parse(localStorage.getItem("usuarioCorrente"));
  var usuarioCorrenteSession = JSON.parse(sessionStorage.getItem("usuarioCorrente"));
  console.log("Checando login...");
  if (!usuarioCorrenteLocal && !usuarioCorrenteSession) {
    window.location.href = "index.html";
  }
}

function removerCurso() {
  localStorage.removeItem("curso_atual")
}


// efeito apagando texto
const typedTextSpan = document.querySelector(".escreve-texto");
const cursorSpan = document.querySelector(".cursor");

const textArray = ["unidos", "criados", "vivos","feitos"];
const typingDelay = 200;
const erasingDelay = 100;
const newTextDelay = 2000;
let textArrayIndex = 0;
let charIndex = 0;

function type() {
if (charIndex < textArray[textArrayIndex].length) {
if(!cursorSpan.classList.contains("typing")) cursorSpan.classList.add("typing");
typedTextSpan.textContent += textArray[textArrayIndex].charAt(charIndex);
charIndex++;
setTimeout(type, typingDelay);
} 
else {
cursorSpan.classList.remove("typing");
setTimeout(erase, newTextDelay);
}
}

function erase() {
if (charIndex > 0) {
if(!cursorSpan.classList.contains("typing")) cursorSpan.classList.add("typing");
typedTextSpan.textContent = textArray[textArrayIndex].substring(0, charIndex-1);
charIndex--;
setTimeout(erase, erasingDelay);
} 
else {
cursorSpan.classList.remove("typing");
textArrayIndex++;
if(textArrayIndex>=textArray.length) textArrayIndex=0;
setTimeout(type, typingDelay + 1100);
}
}

document.addEventListener("DOMContentLoaded", function() { 
if(textArray.length) setTimeout(type, newTextDelay + 250);
});
