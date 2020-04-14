package Client;

import Shared.Packet;

public class ReadMessageBus implements Runnable
{
    private ClientController clientController;
    private Thread workerThread;
    private boolean isRunning = false;


    public ReadMessageBus(ClientController clientController)
    {
        this.clientController = clientController;
    }



    @Override
    public void run()
    {
        while (isRunning)
        {
            Packet response = clientController.getClient().getNextResponseFromServer();

            if (response != null)
            {
                if (response.getRequest().equals(Packet.REGISTER_CLIENT))
                {
                    // registration successful
                    System.out.println("You have been registered");
                }
                else
                {
                    // registration was not successful
                }
            }
        }
    }
}
