from zeep import Client

url = "http://localhost:7000/ws/EstudianteWebServices?wsdl"
client = Client(url)

while True:
    print("\nPor favor, selecciona una opción:")
    print("1. Listar todos los estudiantes")
    print("2. Añadir un nuevo estudiante")
    print("3. Borrar un estudiante")
    print("4. Consultar un estudiante por su matricula")
    print("5. Salir")

    opcion = input("Opción: ")

    if opcion == "1":
        # Listar todos los estudiantes
        print("\nLista de todos los estudiantes:")
        studentList = client.service.getListaEstudiante()
        for x in studentList:
            print(x)
    elif opcion == "2":
        # Crear un nuevo estudiante
        print("\nCreando un nuevo estudiante...")
        matricula = int(input("Matricula: "))
        nombre = input("Nombre: ")
        carrera = input("Carrera: ")

        # Crear Estudiante
        estudiante = client.get_type('ns0:estudiante')(matricula=matricula, nombre=nombre, carrera=carrera)

        newEstudiante = client.service.crearEstudiante(estudiante)
        print(f"Estudiante {nombre} ha sido creado exitosamente.")
    elif opcion == "3":
        # Borrar un estudiante
        print("\nEliminando un estudiante...")
        matricula = int(input("Matricula: "))
        estudiante = client.service.eliminarEstudiante(matricula)
        if estudiante is not None:
            print(f"Estudiante con matrícula {matricula} eliminado exitosamente.")
        else:
            print(f"No se pudo eliminar al estudiante con matrícula {matricula}.")
    elif opcion == "4":
        # Consultar un estudiante por su matrícula
        print("\nConsultando un estudiante...")
        matricula = int(input("Matricula: "))  # Convertir entrada a entero
        estudiante = client.service.getEstudiante(matricula)
        if estudiante is not None:
            print(f"Estudiante encontrado: {estudiante}")
        else:
            print(f"No se encontró al estudiante con matricula {matricula}.")
    elif opcion == "5":
        print("Saliendo...")
        break
    else:
        print("Opción no válida, por favor, intente de nuevo.")
