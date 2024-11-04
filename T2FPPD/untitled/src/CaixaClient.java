import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CaixaClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso : java CaixaClient <servidor> <nomeCliente>");
            System.exit(1);
        }

        try{
            ContaInterface banco = (ContaInterface) Naming.lookup("//" + args[0] + "/Banco");
            Scanner scanner = new Scanner(System.in);
            String nomeCliente = args[1];


            System.out.println("Bem-vindo ao Caixa Automático!");
            System.out.print("Digite o nome do cliente: ");
            nomeCliente = scanner.nextLine();

            boolean exit = false;
            while (!exit) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Consultar Saldo");
                System.out.println("2. Depositar");
                System.out.println("3. Sacar");
                System.out.println("4. Depositar com Falha Simulada");
                System.out.println("5. Sacar com Falha Simulada");
                System.out.println("0. Sair");
                System.out.print("Opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer de entrada

                switch (opcao) {
                    case 1:
                        double saldo = banco.consultarSaldo(nomeCliente);
                        if (saldo >= 0) {
                            System.out.println("Saldo atual: " + saldo);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;

                    case 2:
                        System.out.print("Digite o valor para depósito: ");
                        double valorDeposito = scanner.nextDouble();
                        if (banco.depositar(nomeCliente, valorDeposito)) {
                            System.out.println("Depósito de " + valorDeposito + " realizado com sucesso.");
                        } else {
                            System.out.println("Falha ao depositar. Verifique se a conta existe.");
                        }
                        break;

                    case 3:
                        System.out.print("Digite o valor para saque: ");
                        double valorSaque = scanner.nextDouble();
                        if (banco.sacar(nomeCliente, valorSaque)) {
                            System.out.println("Saque de " + valorSaque + " realizado com sucesso.");
                        } else {
                            System.out.println("Falha ao sacar. Verifique o saldo ou se a conta existe.");
                        }
                        break;

                    case 4:
                        System.out.print("Digite o valor para depósito (com falha simulada): ");
                        valorDeposito = scanner.nextDouble();
                        try {
                            // Realiza o depósito e simula uma falha logo após
                            if (banco.depositar(nomeCliente, valorDeposito)) {
                                System.out.println("Depósito de " + valorDeposito + " realizado com sucesso.");
                                throw new RemoteException("Simulação de falha após depósito.");
                            }
                        } catch (RemoteException e) {
                            System.out.println("Falha simulada: o depósito pode ter sido processado corretamente.");
                        }
                        break;

                    case 5:
                        System.out.print("Digite o valor para saque (com falha simulada): ");
                        valorSaque = scanner.nextDouble();
                        try {
                            // Realiza o saque e simula uma falha logo após
                            if (banco.sacar(nomeCliente, valorSaque)) {
                                System.out.println("Saque de " + valorSaque + " realizado com sucesso.");
                                throw new RemoteException("Simulação de falha após saque.");
                            }
                        } catch (RemoteException e) {
                            System.out.println("Falha simulada: o saque pode ter sido processado corretamente.");
                        }
                        break;

                    case 0:
                        exit = true;
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

            System.out.println("Obrigado por usar o Caixa Automático!");
            scanner.close();
        }catch (Exception e){
            System.err.println("Erro no cliente de caixa automático: ");
            e.printStackTrace();
        }
    }
}
