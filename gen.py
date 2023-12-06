import random

def generate_test_case(n):
    money = [random.randint(500, 2000) for _ in range(n)]
    relationships = [list(range(1, n+1)) for _ in range(1,n+1)]
    return money, relationships

def generate_input(num_cases, num_people):
    test_cases = [generate_test_case(num_people) for _ in range(num_cases)]
    return test_cases

def write_input_to_file(test_cases, filename):
    with open(filename, 'w') as file:
        file.write(f"{len(test_cases)}\n")
        for money, relationships in test_cases:
            file.write(f"{' '.join(map(str, money))}\n")
            i = 0
            for r in relationships:
                r.pop(i)
                file.write(f"{' '.join(map(str, r))}\n")
                i+=1

# Generate and save 100 test cases, each with 1000 people
test_cases = generate_input(1, 10000)
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