import java.rmi.Naming;

public class CaixaClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso : java CaixaClient <servidor> <nomeCliente>");
            System.exit(1);
        }

        try{
            ContaInterface banco = (ContaInterface) Naming.lookup("//" + args[0] + "/Banco");
            String nomeCliente = args[1];

            System.out.println("Saldo inicial: " + banco.consultarSaldo(nomeCliente));
            banco.depositar(nomeCliente, 100.0);
            System.out.println("Saldo após depósito com falha simulada: " + banco.consultarSaldo(nomeCliente));
        }catch (Exception e){
            System.err.println("Erro no cliente de caixa automático: ");
            e.printStackTrace();
        }
    }
}
