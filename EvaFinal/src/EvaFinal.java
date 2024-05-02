import java.util.ArrayList;
import java.util.Scanner;

public class EvaFinal {
    
    // Definición de constantes
    static final int MAX_ASIENTOS = 5;

    // Clase para representar una venta de entrada
    static class VentaEntrada {
        int idVenta;
        String tipoEntrada;
        String promocion;
        String ubicacionAsiento;
        int precioFinal;
        int precioBase;
    }

    // Clase para representar un asiento del teatro
    static class AsientoTeatro {
        int numero;
        boolean disponible;
    }

    // Clase para representar una promoción
    static class Promocion {
        String nombre;
        int descuento;

        Promocion(String nombre, int descuento) {
            this.nombre = nombre;
            this.descuento = descuento;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<VentaEntrada> ventas = new ArrayList<>();
        AsientoTeatro[] asientos = new AsientoTeatro[MAX_ASIENTOS];
        ArrayList<Promocion> promociones = new ArrayList<>();

        // Inicialización de los asientos del teatro
        for (int i = 0; i < MAX_ASIENTOS; i++) {
            asientos[i] = new AsientoTeatro();
            asientos[i].numero = i + 1;
            asientos[i].disponible = true;
        }

        // Agregar promociones
        promociones.add(new Promocion("Estudiante", 10));
        promociones.add(new Promocion("Tercera Edad", 15));
        promociones.add(new Promocion("Sin descuento", 0));

        boolean salir = false;

        do {
            System.out.println("""
                    [1] Venta de Entradas
                    [2] Modificar Entrada
                    [3] Eliminar Entrada
                    [4] Imprimir boleta
                    [5] Resumen de ventas
                    [0] Salir del sistema
                    Ingresar opcion:""");
            int opmenu = scanner.nextInt();
            switch (opmenu) {
                case 1:
                    ventaEntrada(ventas, asientos, promociones, scanner);
                    break;
                case 2:
                    if (ventas.isEmpty()) {
                        System.out.println("El carrito de compras se encuentra vacio.");
                    } else {
                        modificarEntrada(ventas, asientos, promociones, scanner);
                    }
                    break;
                case 3:
                    if (ventas.isEmpty()) {
                        System.out.println("El carrito de compras se encuentra vacio.");
                    } else {
                        eliminarEntrada(ventas, asientos, scanner);
                    }
                    break;
                case 4:
                    imprimirBoleta(ventas);
                    break;
                case 5:
                    resumenVentas(ventas);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("Ingrese una opcion valida...");
            }
        } while (!salir);
        
        scanner.close();
    }

    // Función para realizar la venta de una entrada
    static void ventaEntrada(ArrayList<VentaEntrada> ventas, AsientoTeatro[] asientos,
                             ArrayList<Promocion> promociones, Scanner scanner) {
        System.out.println("Seleccione el tipo de entrada:");
        System.out.println("[1] VIP");
        System.out.println("[2] Platea");
        System.out.println("[3] General");
        int tipoEntrada = scanner.nextInt();

        if (tipoEntrada < 1 || tipoEntrada > 3) {
            System.out.println("Tipo de entrada invalida.");
            return;
        }

        // Mostrar promociones disponibles
        System.out.println("Seleccione la promocion:");
        for (int i = 0; i < promociones.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + promociones.get(i).nombre);
        }
        int opcionPromocion = scanner.nextInt();
        if (opcionPromocion < 1 || opcionPromocion > promociones.size()) {
            System.out.println("Promocion invalida.");
            return;
        }
        Promocion promocionSeleccionada = promociones.get(opcionPromocion - 1);

        // Mostrar asientos disponibles
        System.out.println("Asientos disponibles:");
        for (int i = 0; i < asientos.length; i++) {
            if (asientos[i].disponible) {
                System.out.println("Asiento " + asientos[i].numero);
            }
        }
        System.out.println("Seleccione el numero del asiento:");
        int numAsiento = scanner.nextInt();
        if (numAsiento < 1 || numAsiento > asientos.length || !asientos[numAsiento - 1].disponible) {
            System.out.println("Asiento invalido o no disponible.");
            return;
        }

        // Registrar la venta
        VentaEntrada venta = new VentaEntrada();
        venta.idVenta = ventas.size() + 1;
        venta.tipoEntrada = tipoEntrada == 1 ? "VIP" : (tipoEntrada == 2 ? "Platea" : "General");
        venta.promocion = promocionSeleccionada.nombre;
        venta.ubicacionAsiento = "Asiento " + numAsiento;
        venta.precioBase = tipoEntrada == 1 ? 20000 : (tipoEntrada == 2 ? 15000 : 10000);
        venta.precioFinal = venta.precioBase - (venta.precioBase * promocionSeleccionada.descuento / 100);

        // Marcar el asiento como ocupado
        asientos[numAsiento - 1].disponible = false;

        ventas.add(venta);
        System.out.println("¡Venta registrada exitosamente!");
    }

    // Función para modificar una entrada del carrito de compras
    static void modificarEntrada(ArrayList<VentaEntrada> ventas, AsientoTeatro[] asientos,
                                 ArrayList<Promocion> promociones, Scanner scanner) {
        System.out.println("Ingrese el ID de la venta a modificar:");
        int idVenta = scanner.nextInt();
        boolean encontrado = false;
        for (VentaEntrada venta : ventas) {
            if (venta.idVenta == idVenta) {
                encontrado = true;
                // Imprimir detalles de la venta
                System.out.println("Detalles de la venta a modificar:");
                System.out.println("ID de Venta: " + venta.idVenta);
                System.out.println("Tipo de Entrada: " + venta.tipoEntrada);
                System.out.println("Promoción: " + venta.promocion);
                System.out.println("Ubicación del Asiento: " + venta.ubicacionAsiento);
                System.out.println("Precio Final: " + venta.precioFinal);

                // Permitir modificar la promoción
                System.out.println("Seleccione una nueva promoción:");
                for (int i = 0; i < promociones.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + promociones.get(i).nombre);
                }
                int opcionPromocion = scanner.nextInt();
                if (opcionPromocion < 1 || opcionPromocion > promociones.size()) {
                    System.out.println("Promoción inválida. No se realizó la modificación.");
                    return;
                }
                Promocion promocionSeleccionada = promociones.get(opcionPromocion - 1);
                venta.promocion = promocionSeleccionada.nombre;
                venta.precioFinal = venta.precioBase - (venta.precioBase * promocionSeleccionada.descuento / 100);

                System.out.println("Venta modificada exitosamente.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("ID de venta no encontrado.");
        }
    }

    // Función para eliminar una entrada del carrito de compras
    static void eliminarEntrada(ArrayList<VentaEntrada> ventas, AsientoTeatro[] asientos, Scanner scanner) {
        System.out.println("Ingrese el ID de la venta a eliminar:");
        int idVenta = scanner.nextInt();
        boolean encontrado = false;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).idVenta == idVenta) {
                encontrado = true;
                // Liberar el asiento
                String ubicacionAsiento = ventas.get(i).ubicacionAsiento;
                int numAsiento = Integer.parseInt(ubicacionAsiento.substring(ubicacionAsiento.indexOf(" ") + 1));
                asientos[numAsiento - 1].disponible = true;
                // Eliminar la venta
                ventas.remove(i);
                System.out.println("Venta eliminada exitosamente.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("ID de venta no encontrado.");
        }
    }

    // Función para imprimir la boleta de una venta
    static void imprimirBoleta(ArrayList<VentaEntrada> ventas) {
        System.out.println("----- Boleta -----");
        for (VentaEntrada venta : ventas) {
            System.out.println("ID de Venta: " + venta.idVenta);
            System.out.println("Tipo de Entrada: " + venta.tipoEntrada);
            System.out.println("Promoción: " + venta.promocion);
            System.out.println("Ubicación del Asiento: " + venta.ubicacionAsiento);
            System.out.println("Precio Final: " + venta.precioFinal);
            System.out.println("-------------------");
        }
    }

    // Función para imprimir el resumen de ventas
    static void resumenVentas(ArrayList<VentaEntrada> ventas) {
        System.out.println("----- Resumen de Ventas -----");
        for (VentaEntrada venta : ventas) {
            System.out.println("ID de Venta: " + venta.idVenta);
            System.out.println("Tipo de Entrada: " + venta.tipoEntrada);
            System.out.println("Promoción: " + venta.promocion);
            System.out.println("Ubicación del Asiento: " + venta.ubicacionAsiento);
            System.out.println("Precio Final: " + venta.precioFinal);
            System.out.println("-------------------");
        }
    }
}
