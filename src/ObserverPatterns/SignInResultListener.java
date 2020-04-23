package ObserverPatterns;

import Shared.UserInformation;

public interface SignInResultListener
{
    public void updateSignInResult(String message, UserInformation userInformation);
}
