import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
//O


public class Problema3 {
	
	private Set<Short>[] adj;
	private int[] dineroPersonas;
	private short[] personas;
	
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
					final Set<Short> set = new HashSet<>();
					
					// a cada persona se le resta 1 para que corresponda a los indices
					Arrays.stream(adjStr)
			        .mapToInt(f -> (Short.parseShort(f) - 1))
			        .forEach(k -> {
			            List<Short> list = new ArrayList<>();
			            list.add((short) k);
			            set.addAll(list);
			        });
					
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
		final short[] Bvacio = Arrays.copyOf(personas, personas.length) ;
		for (short persona : Bvacio) {
			dineroEstado += dineroPersonas[persona];
			
		}
		
		final Estado vacio = new Estado(new short[0], Bvacio, 0, dineroEstado);
		System.out.println("suc vacio " +dineroEstado);
		colaPrioridad.add(vacio);
		while (!colaPrioridad.isEmpty()) {
			final Estado estado = colaPrioridad.poll();

            
			if (estado.isBEmpty()) {
		        System.out.print("RESULTADO {");
		        for (short a : estado.getA()) {
					System.out.print(a + ",");
				}
		        System.out.println("} Dinero: " + estado.getDinero() + " Num Personas " + estado.getA().length +
		                " Tamaño de la cola " + colaPrioridad.size());
		        break;
		    }
		    for (Estado hijo : sucs(estado)) {
			        System.out.println("Estado { Dinero: " + estado.getDinero() + " Num Personas " + estado.getA().length +
			                " Tamaño de la cola " + colaPrioridad.size());
			        colaPrioridad.add(hijo);
		    	
		    }
		   
            	        
        }
		
	}
	
	private ArrayList<Estado> sucs(Estado estado) {
		final ArrayList<Estado> sucs = new ArrayList<>();
		for (short num : estado.getB()) {
			short[] A = Arrays.copyOf(estado.getA(), estado.getA().length + 1);
            A[estado.getA().length] = num;
			
            ArrayList<Short> bArrayList = new ArrayList<>();
            
            for (short elemento : estado.getB()) {
				if (adj[num].contains(elemento)) {
					bArrayList.add(elemento);
				}
			}
            
            short[] B = new short[bArrayList.size()];
		    int dineroB = 0;
		    int i=0;
	        for (short elemento : bArrayList) {
                B[i]=elemento;
	        	dineroB += dineroPersonas[elemento];
	        	i++;

            }

						
			sucs.add(new Estado(A, B, estado.getDineroA() + dineroPersonas[num], dineroB));
			
		}
		
		return sucs;
		
	}
	
	public void iniciarAdj (int num) {
		this.adj = new HashSet[num];
	    this.personas = new short[num];
	    
	    for (short i = 0; i < num; i++) {
	    	this.personas[i] = i;
        }
	}
	
	public void addAdj (int i, Set<Short> set) {
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
	private short[] A;
	private short[] B;
	private int dineroA = 0;
	private int dineroTotal = 0;
	
	public Estado(short[] A, short[] B, int dineroA, int dineroB) {
		this.A = A;
		this.B = B;
		this.dineroA = dineroA;
		dineroTotal = dineroA + dineroB;
	}

	public boolean isBEmpty() {
		return B.length == 0;
	}
	
	public int getDinero() {
		return dineroTotal;
	}

	public short[] getB() {
        return B;
    }
	

	public int getBSize() {
		return B.length;
	}
	
	public short[] getA() {
        return A;
    }
	public Set<Short> getAset() {
		Set<Short> setA =  IntStream.range(0, A.length).mapToObj(s -> A[s]).collect(Collectors.toSet());
		return setA;
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
		return Objects.hash(getAset());
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
		return Objects.equals(this.getAset(), other.getAset());
	}


	
	

	


}
