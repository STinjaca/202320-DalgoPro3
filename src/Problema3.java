import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Problema3 {
	
	private Set<Integer>[] adj;
	private int[] dineroPersonas;
	private Set<Integer> personas;
	
	public static void main(String[] args) throws Exception {
		Problema3 instancia = new Problema3();
		


			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		
			String line = br.readLine();
			int casos = Integer.parseInt(line);
			line = br.readLine();
			for(int i=0;i<casos && line!=null && line.length()>0 && !"0".equals(line);i++) {
				System.out.println("caso " + i);
				final String [] dataStr = line.split(" ");
				final int[] dinero = Arrays.stream(dataStr).mapToInt(f->Integer.parseInt(f)).toArray();
			
				// Guarda el aprote de cada persona
				instancia.setDinero(dinero);
				final int num = dinero.length;
				
				//Crea un conjuto para cada persona y sus conocidos
				instancia.iniciarAdj(num);
		        
				for (int j = 0; j<num; j++) {
					line = br.readLine();
					final String [] adjStr = line.split(" ");
					final Set<Integer> set = new HashSet<>();
					
					// a cada persona se le resta 1 para que corresponda a los indices
					Arrays.stream(adjStr).mapToInt(f->Integer.parseInt(f)-1).forEach(set::add);
					
					// Añadirle a cada persona los que conoce
					instancia.addAdj(j, set);

				}
				//Resolver el problema
				instancia.resolver();
				line = br.readLine();
			
		}
		
		
		
	}
	
	public void resolver() {
		final Queue<Estado> colaPrioridad = new PriorityQueue<>();
		int dineroEstado = 0;
		for (Integer persona : personas) {
			dineroEstado += dineroPersonas[persona];
		}
		
		Estado vacio = new Estado(new ArrayList<>(), new ArrayList<>(personas), 0, dineroEstado);
        
		System.out.println("suc vacio " +dineroEstado);
		colaPrioridad.add(vacio);
		while (!colaPrioridad.isEmpty()) {
			final Estado estado = colaPrioridad.poll();

            
            if(estado.getB().isEmpty()) {
	        	System.out.print( "RESULTADO {");
	            estado.getA().forEach(elemento -> System.out.print((elemento+1) + ","));
		        System.out.println("} Dinero: " + estado.getDinero() + " Num Personas " +estado.getA().size()+ " Tamano de la cola " +colaPrioridad.size());
		        break;
	        }
            
            for (Estado hijo : sucs(estado)) {
            	System.out.println( "Estado { Dinero: " + estado.getDinero() + " Num Personas " +estado.getA().size()+ " Tamano de la cola " +colaPrioridad.size());
            	colaPrioridad.add(hijo);
            	
			}
            	        
        }
		
	}
	
	private ArrayList<Estado> sucs(Estado estado) {
		final ArrayList<Estado> sucs = new ArrayList<>();
		for (Integer num : estado.getB()) {
			ArrayList<Integer> A = new ArrayList<>(estado.getA());
			A.add(num);
			
			ArrayList<Integer> B = new ArrayList<>(estado.getB());
			B.retainAll(adj[num]);
			
	
			 int dineroB = 0;
		        for (Integer elemento : B) {
		        	dineroB += dineroPersonas[elemento];
		        }
			
			Estado suc = new Estado(A, B, estado.getDineroA() + dineroPersonas[num], dineroB);
			
			sucs.add(suc);
			
		}
		
		return sucs;
		
	}
	
	public void iniciarAdj (int num) {
		this.adj = new HashSet[num];
	    this.personas = new HashSet<>();
	    
	    for (int i = 0; i < num; i++) {
	    	this.personas.add(i);
        }
	}
	
	public void addAdj (int i, Set<Integer> set) {
		this.adj[i] = set;
	}

	public int[] getDinero() {
		return dineroPersonas;
	}
	public void setDinero(int[] dinero) {
		this.dineroPersonas = dinero;
	}
	

	
}

// Cada estado de la busqueda
class Estado implements Comparable<Estado> {
	private ArrayList<Integer> A;
	private ArrayList<Integer> B;
	private int dineroA = 0;
	private int dineroTotal = 0;
	
	public Estado(ArrayList<Integer> A, ArrayList<Integer> B, int dineroA, int dineroB) {
		this.A = A;
		this.B = B;
		this.dineroA = dineroA;
		dineroTotal = dineroA + dineroB;
	}
	
	public boolean isAEmpty() {
		return A.isEmpty();

	}

	public boolean isBEmpty() {
		return B.isEmpty();
	}
	
	public int getDinero() {
		return dineroTotal;
	}

	public ArrayList<Integer> getB() {
		return B;
	}
	

	public int getBSize() {
		return B.size();
	}
	
	public ArrayList<Integer> getA() {
        return A;
    }
	
	
    public int getDineroA() {
		return dineroA;
	}

	@Override
    public int compareTo(Estado otro) {
        return Integer.compare(otro.getDinero(), this.getDinero());
    }

	@Override
	public int hashCode() {
		return Objects.hash(A);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return Objects.equals(A, other.A);
	}


	
	

	


}
