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


public class Problema3_1 {
	
	private Set<Short>[] adj;
	private int[] dineroPersonas;
	
	public static void main(String[] args) throws Exception {
		Problema3_1 instancia = new Problema3_1();
		


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
					final Set<Short> list = new HashSet<>();
					
					// a cada persona se le resta 1 para que corresponda a los indices
					Arrays.stream(adjStr)
			        .mapToInt(f -> (Short.parseShort(f) - 1))
			        .forEach(k -> {
			            list.add((short) k);
			        });
					
					// Añadirle a cada persona los que conoce
					instancia.addAdj(j, list);

				}
				//Resolver el problema
				instancia.resolver();
				line = br.readLine();
			
		}
		
		
		
	}
	
	public void resolver() {
		final Queue<Estado> colaPrioridad = new PriorityQueue<>();
		
		Set<Short> B = new HashSet<>();
		int dineroEstado = 0;
		for (short i = 0; i< adj.length; i++) {
			dineroEstado += dineroPersonas[i];
			B.add(i);
		}
		
		final Estado vacio = new Estado(new short[0], B, 0, dineroEstado);
		System.out.println("suc vacio " +dineroEstado);
		int revisados = 0;
		colaPrioridad.add(sucs(vacio));
		while (!colaPrioridad.isEmpty()) {
			final Estado estado = colaPrioridad.poll();
			revisados++;
			
			System.out.println("Estado { Dinero: " + estado.getDinero() + " Num Personas " + estado.getA().length +
	                " Tamaño de la cola " + colaPrioridad.size() + " Probados:" + revisados);
            
			if (estado.isBEmpty()) {
		        System.out.print("RESULTADO {");
		        for (short a : estado.getA()) {
					System.out.print(a + ",");
				}
		        System.out.println("} Dinero: " + estado.getDinero() + " Num Personas " + estado.getA().length +
		                " Tamaño de la cola " + colaPrioridad.size());
		        break;
		    }
			

	    	colaPrioridad.add(sucs(estado));
			
		    
		   
            	        
        }
		
	}
	
	private Estado sucs(Estado estado) {
		short numMayor = -1;
		Estado result = new Estado(null,null, 0,0);
		System.err.println("Tamaño incial " + estado.getB().size());
		for (short num : estado.getB()) {
			
            Set<Short> B = new HashSet<>(estado.getB());
            B.retainAll(adj[num]);
            
            int dineroB = (int) B.stream()
            .mapToDouble(o -> dineroPersonas[o])
            .sum();
            
	        
	        Estado tem = new Estado(null,null, estado.getDineroA() + dineroPersonas[num], dineroB);
	        if(result.compareTo(tem) >= 0) {
	        	result = tem;
	        	numMayor = num;
	        }
       
		}
		System.err.println("Indice mayor " + numMayor);
		if(numMayor >= 0) {
			short[] A = Arrays.copyOf(estado.getA(), estado.getA().length + 1);
            A[A.length-1] = numMayor;
			System.err.println("Tamno de A "+ A.length);
			estado.setA(A);
			estado.getB().retainAll(adj[numMayor]);
            int dineroB = (int) estado.getB().stream()
                    .mapToDouble(o -> dineroPersonas[o])
                    .sum();
	        
	        estado.setDinero(dineroPersonas[numMayor], dineroB);
	        
	        return estado;

		}else {
			return null;
		}
		
	}
	
	private ArrayList<Estado> sucsIniciales(Estado estado) {
		final ArrayList<Estado> sucs = new ArrayList<>();
		for (short num : estado.getB()) {
			short[] A =  Arrays.copyOf(estado.getA(), estado.getA().length+1);
			A[estado.getA().length] =num;
			
			Set<Short> B = new HashSet<>(estado.getB());
            B.retainAll(adj[num]);

            int dineroB = 0;
	        for (short elemento : B) {
	        	dineroB += dineroPersonas[elemento];
            }

			sucs.add(new Estado(A, B, estado.getDineroA() + dineroPersonas[num], dineroB));
		}
		
		return sucs;
		
	}
	
	public void iniciarAdj (int num) {
		this.adj = new Set[num];
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
	

	
// Cada estado de la busqueda
	class Estado implements Comparable<Estado> {
		private short[] A;
		private Set<Short> B;
		private int dineroA = 0;
		private int dineroTotal = 0;
		
		public Estado(short[] A, Set<Short> B, int dineroA, int dineroB) {
			this.A = A;
			this.B = B;
			this.dineroA = dineroA;
			dineroTotal = dineroA + dineroB;
		}
		
		public boolean isBEmpty() {
			return B.isEmpty();
		}
		
		public int getDinero() {
			return dineroTotal;
		}
		
		public Set<Short> getB() {
			return B;
		}
		
		
		public int getBSize() {
			return B.size();
		}
		
		public short[] getA() {
			return A;
		}	
		
		public void setA(short[] A) {
			this.A = A;
		}
		
		public int getDineroTotal() {
			return dineroTotal;
		}

		public void setDinero(int dineroAdicional, int dineroB) {
			this.dineroA += dineroAdicional;
			dineroTotal = dineroA + dineroB;
		}

		public void setDineroA(int dineroA) {
			this.dineroA = dineroA;
		}

		public int getDineroA() {
			return dineroA;
		}
		
		@Override
		public int compareTo(Estado otro) {
			if(otro.getDinero() < this.getDinero()) {
				return -1;
			} else if(otro.getDinero() > this.getDinero()) {
				return 1;
			}
	
			return 0;
		}
				
		
		
	}
}

