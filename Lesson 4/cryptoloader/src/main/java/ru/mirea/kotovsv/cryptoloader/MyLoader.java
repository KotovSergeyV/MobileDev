package ru.mirea.kotovsv.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public	class	MyLoader	extends AsyncTaskLoader<String> {
    private final Bundle args;
    public	static	final	String	ARG_WORD	=	"word";

    public	MyLoader(@NonNull Context context, Bundle args)	{
        super(context);
        this.args = args;
    }

    @Override
    protected	void	onStartLoading()	{
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public	String	loadInBackground()	{
        byte[] cryptText = args.getByteArray(ARG_WORD);
        byte[] key = args.getByteArray("key");

        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");

        return decryptMsg(cryptText, originalKey);
    }

    public	static	String	decryptMsg(byte[]	cipherText,	SecretKey secret){
        /*	Decrypt	the	message	*/
        try	{
            Cipher cipher	=	Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,	secret);
            return	new	String(cipher.doFinal(cipherText));
        }
        catch	(NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                       | BadPaddingException | InvalidKeyException e)
        {
            throw	new	RuntimeException(e);
        }
    }
}