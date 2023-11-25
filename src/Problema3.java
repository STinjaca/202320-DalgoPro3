import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Problema3 {
	
	private Set<Integer>[] adj;
	private int[] dinero;
	private Set<Integer> personas;
	
	public static void main(String[] args) throws Exception {
		Problema3 instancia = new Problema3();
		
		try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		) { 
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
					System.out.println(j);
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
		is.close();
		br.close();
		}
		
		
		
	}
	
	public void resolver() {
		Queue<Estado> colaPrioridad = new PriorityQueue<>();
		
		int puntaje = 0;
		for (Integer persona : personas) {
			puntaje += dinero[persona];
		}
		
		Estado vacio = new Estado(new HashSet<>(), new HashSet<>(personas), puntaje);
        
		System.out.println("suc vacio " +puntaje);
		colaPrioridad.addAll(sucs(vacio));
		while (!colaPrioridad.isEmpty()) {
            Estado estado = colaPrioridad.poll();
            System.out.print( "suc {");
            estado.getA().forEach(elemento -> System.out.print((elemento+1) + ","));
	        System.out.println("} Puntaje: " + estado.getPuntaje());
	        colaPrioridad.addAll(sucs(estado));
        }
		
	}
	
	private ArrayList<Estado> sucs(Estado estado) {
		final ArrayList<Estado> sucs = new ArrayList<>();
		for (Integer num : estado.getB()) {
			final Set<Integer> A;
			final Set<Integer> B;
			
			if (estado.isAEmpty()) {
				A = new HashSet<>();
				B = adj[num];
				
			}else {
				A = new HashSet<>(estado.getA());;
				B = new HashSet<>(estado.getB());
				B.retainAll(adj[num]);
			}
			
			A.add(num);
			
	
	        int puntaje[] = {0};
			B.forEach(elemento -> puntaje[0] += dinero[elemento]);
			A.forEach(elemento -> puntaje[0] += dinero[elemento]);
			
			final Estado suc = new Estado(A, B, puntaje[0]);
			
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
		return dinero;
	}
	public void setDinero(int[] dinero) {
		this.dinero = dinero;
	}
	

	
}

// Cada estado de la busqueda
class Estado implements Comparable<Estado> {
	private Set<Integer> A;
	private Set<Integer> B;
	private int puntaje = 0;
	
	public Estado(Set<Integer> A, Set<Integer> B, int puntaje) {
		this.A = A;
		this.B = B;
		this.puntaje = puntaje;
	}
	
	public boolean isAEmpty() {
		return A.isEmpty();

	}

	public boolean isBEmpty() {
		return B.isEmpty();
	}

	public void addPersona(Integer nuevaPersona) {
		this.A.add(nuevaPersona);
	}

	
	public int getPuntaje() {
		return puntaje;
	}

	public Set<Integer> getB() {
		return B;
	}
	

	public int getBSize() {
		return B.size();
	}
	
	public Set<Integer> getA() {
		return A;
	}
	
	
    @Override
    public int compareTo(Estado otro) {
        return Integer.compare(otro.puntaje, this.puntaje);
    }
}
