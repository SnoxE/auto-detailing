<template>
  <div class="flex flex-col justify-center text-white">
    <div class="mx-auto mt-10 flex pt-6 text-2xl">
      <span>Witaj ponownie</span>
    </div>
    <form class="login-form z-2 mx-auto flex w-80 flex-col p-6" @submit.prevent="onSubmit">
      <div class="mx-auto my-2 flex w-full flex-col gap-4">
        <InputField
          id="email"
          v-model="username"
          class="h-10 rounded bg-light-gray pl-2"
          type="text"
          name="email"
          placeholder="you@gmail.com"
          required
        />
        <InputField
          id="password"
          v-model="password"
          class="h-10 rounded bg-light-gray pl-2"
          type="password"
          name="email"
          placeholder="password"
          required
        />
      </div>
      <button
        type="submit"
        class="submit-btn mx-auto mt-6 w-full rounded-lg bg-gray-500 p-2 hover:bg-mcl-orange"
      >
        Zaloguj
      </button>
    </form>
    <div class="mx-auto flex">
      <span class="p-1 text-sm">Nie masz konta?</span>
      <router-link
        to="/rejestracja"
        class="p-1 text-sm underline underline-offset-2 hover:text-mcl-orange"
        >Zarejestruj się</router-link
      >
    </div>
  </div>
</template>

<script>
import InputField from '@/components/InputField.vue'
import { useAuthStore } from '@/stores/auth'

export default {
  name: 'LoginForm',
  components: { InputField },
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    onSubmit() {
      if (this.username != '' && this.password != '') {
        useAuthStore().login(this.username, this.password)
      }
    }
  }
}
</script>
