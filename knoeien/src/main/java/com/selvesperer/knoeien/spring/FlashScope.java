package com.selvesperer.knoeien.spring;

import javax.faces.context.Flash;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import com.selvesperer.knoeien.utils.JSFUtils;

public class FlashScope implements Scope {

   public Object get(String name, ObjectFactory<?> objectFactory) {
      Flash flashScope = JSFUtils.getFlashScope();

      if(flashScope.containsKey(name)) {
         return flashScope.get(name);
      }
      else {
         Object object = objectFactory.getObject();
         flashScope.putNow(name, object);
         flashScope.keep(name);

         return object;
      }
   }

   public Object remove(String name) {
      return JSFUtils.getFlashScope().remove(name);
   }

   public String getConversationId() {
      return null;
   }

   public void registerDestructionCallback(String name, Runnable callback) {
      // Not supported
   }

   public Object resolveContextualObject(String key) {
      return null;
   }
}
