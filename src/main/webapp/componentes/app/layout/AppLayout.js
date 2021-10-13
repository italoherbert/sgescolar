
class AppLayout {
	
	menuVisivel = true;
	
	onCarregado( jsObj, params ) {				
		jsObj.configuraMenu();
		
		sistema.carregaPagina( 'pessoa-tela' );						    
	}
	
	configuraMenu() {
		const instance = this;
		
		const imgMenuToggle = document.getElementById("img-menu-toggle");
		imgMenuToggle.src = sistema.getIconeSrc( 'bt-menu-toggle-left' );			
		
		const sidebarToggle = document.body.querySelector('#sidebarToggle');	
	    sidebarToggle.addEventListener('click', event => {
	        event.preventDefault();
	        document.body.classList.toggle('sb-sidenav-toggled');
	        //localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
	                    
	        instance.menuVisivel = !instance.menuVisivel;
	        
	        if ( instance.menuVisivel === true ) {
				imgMenuToggle.src = sistema.getIconeSrc( 'bt-menu-toggle-left' );
			} else {
				imgMenuToggle.src = sistema.getIconeSrc( 'bt-menu-toggle-right' );
			}
	    });
	}

}