import random

def generate_test_case(n, poblado):
    money = [random.randint(500, 2000) for _ in range(n)]
    relationships = {}
    for persona in range(1,n+1):
        relationships[persona] = set(random.sample(range(persona+1, n + 1), int((n - persona)*poblado)))
        
    for persona in range(1,n+1):
        for conocido in relationships[persona]:
            relationships[conocido].add(persona)
    
    for persona in range(1,n+1):
        if not len(relationships[persona]):
            conocido = random.randint(1, n)
            while conocido == persona:
                conocido = random.randint(1, n)
            relationships[persona].add(conocido)
            relationships[conocido].add(persona)
    
    return money, relationships

def generate_input(num_cases, num_people, poblado):
    test_cases = [generate_test_case(num_people, poblado) for _ in range(num_cases)]
    return test_cases

def write_input_to_file(test_cases, filename):
    with open(filename, 'w') as file:
        file.write(f"{len(test_cases)}\n")
        for money, relationships in test_cases:
            file.write(f"{' '.join(map(str, money))}\n")
            for persona in relationships:
                print(persona, list(relationships[persona]))
                file.write(f"{' '.join(map(str, relationships[persona]))}\n")

# Generate and save 100 test cases, each with 1000 people
#generate_input(numero de casos, numero de personas en los casos, porcentaje en float de conocerce de 0 a 1)
test_cases = generate_input(1, 10000, 0.01)
write_input_to_file(test_cases, "P3.in")

print("Input file 'P3.in' generated successfully.")

"""array = []
for i in 0..this
  array.push(
    		{ '_id':i,
              'nombre':generate('Full Name'),
              'documento':generate('NHS Number'),
              'tipoDocumento':/(Cédula de Ciudadanía|Tarjeta de Identidad|Cédula de Extranjería|Pasaporte|Registro Civil)/.gen,
              'correo':generate('Email Address')
            }
    		)
end

array"""