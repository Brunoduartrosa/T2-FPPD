import java.rmi.Naming;

public class AgenciaClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso : java AgenciaClient <servidor> <nomeCliente>");
            System.exit(1);
        }

        try{
            ContaInterface banco = (ContaInterface) Naming.lookup("//" + args[0] + "/Banco");
            String nomeCliente = args[1];

            banco.abrirConta(nomeCliente);
            banco.depositar(nomeCliente, 500.0);
            System.out.println("Saldo após depósito: " + banco.consultarSaldo(nomeCliente));

            banco.sacar(nomeCliente, 200.0);
            System.out.println("Saldo após saque: " + banco.consultarSaldo(nomeCliente));
        }catch (Exception e){
            System.err.println("Erro no cliente de agência: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
