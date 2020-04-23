package Client;

import ObserverPatterns.SignInResultListener;
import ObserverPatterns.SignUpResultListener;
import Shared.Packet;
import Shared.UserInformation;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReadMessageBus implements Runnable
{
    private ClientController clientController;
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);

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
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }



    @Override
    public void run()
    {
        running.set(true);
        while (running.get())
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
                        System.out.println("SIGN-IN Received");
                        System.out.println(response.getData().toString());
                        signInResultListener.updateSignInResult(response.getData().toString());
                        break;

                    case Packet.SIGN_OUT:
                        break;
                }

            }
        }
    }
}
