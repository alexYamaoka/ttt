package Client;

import ObserverPatterns.SignInResultListener;
import ObserverPatterns.SignUpResultListener;
import Shared.Packet;

public class ReadMessageBus implements Runnable
{
    private ClientController clientController;
    private Thread workerThread;
    private boolean isRunning = false;

    private SignInResultListener signInResultListener;
    private SignUpResultListener signUpResultListener;



    public ReadMessageBus(ClientController clientController)
    {
        this.clientController = clientController;
        signInResultListener = clientController.getSignInController();
        signUpResultListener = clientController.getSignUpController();
    }

    public void start()
    {
        workerThread = new Thread(this);
        workerThread.start();
    }



    @Override
    public void run()
    {
        while (isRunning)
        {
            Packet response = clientController.getClient().getNextResponseFromServer();

            if (response != null)
            {
                switch (response.getRequest())
                {
                    case Packet.REGISTER_CLIENT:
                        signUpResultListener.updateSignInResult(response.getData().toString());
                        break;

                    case Packet.SIGN_IN:
                        signInResultListener.updateSignInResult(response.getData().toString());
                        break;

                    case Packet.SIGN_OUT:
                        break;
                }

            }
        }
    }
}
