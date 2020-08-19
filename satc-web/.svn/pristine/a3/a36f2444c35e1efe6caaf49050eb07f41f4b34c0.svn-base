package test;


import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sat.sisat.estadocuenta.managed.EstadoCuentaManaged;

@ManagedBean
@ViewScoped
public class TestManaged {

	public TestManaged() {
		System.out.println("Test ...");
	}
	
	@PostConstruct
	public void init() {
		
	}
	
	public void test(){
		System.out.println("Test start " + System.currentTimeMillis() + "ms");
		final EstadoCuentaManaged managed = (EstadoCuentaManaged) getManaged("estadoCuentaManaged");
		
		int t=1;
		do{
			new Thread(new Runnable() {
			    public void run() {
			    	long start = System.currentTimeMillis();
			    	try {
			        	//managed.imprimirEstadoCuentaTest();
			        } catch(Exception v) {
			            System.out.println(v);
			        }finally{
						long time = System.currentTimeMillis() - start;
						System.out.println(start+"Invocation took " + time + "ms ---------------------->"+start);
					}
			    }
			}).start();
		}while(++t<=100);
		System.out.println("Test stop " + System.currentTimeMillis() + "ms");
	}
	
	public Object getManaged(String beanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		Application app = fc.getApplication();
		ValueExpression ve = app.getExpressionFactory().createValueExpression(
				fc.getELContext(), String.format("#{%s}", beanName),
				Object.class);
		return ve.getValue(fc.getELContext());
	}
	
}
