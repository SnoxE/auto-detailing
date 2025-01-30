<template>
  <nav id="navbar" class="fixed z-10 w-full bg-transparent">
    <div class="mx-auto flex max-w-screen-xl flex-wrap items-center justify-between">
      <router-link to="/" class="flex items-center p-4">
        <span class="self-center whitespace-nowrap text-2xl font-semibold dark:text-white"
          >Auto Detailing</span
        >
      </router-link>
      <button
        data-collapse-toggle="navbar-default"
        type="button"
        class="inline-flex h-10 w-10 items-center justify-center rounded-lg p-2 text-sm text-white hover:bg-gray-800 focus:outline-none md:hidden"
        @click="toggleMenu()"
      >
        <span class="sr-only">Open main menu</span>
        <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
          <path
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M1 1h15M1 7h15M1 13h15"
          />
        </svg>
      </button>
      <div id="navbar-default" class="hidden w-full p-4 md:block md:w-auto">
        <ul v-show="!loggedIn" class="mt-0 flex space-x-8 bg-transparent p-0 font-medium">
          <li v-for="url in urls" :key="url.name">
            <NavBarItem :url="url.url">{{ url.name }}</NavBarItem>
          </li>
        </ul>
        <ul v-show="loggedIn" class="mt-0 flex space-x-8 bg-transparent p-0 font-medium">
          <li v-for="url in loggedUrls" :key="url.name">
            <NavBarItem
              v-if="url.id === 'user'"
              @mouseover="showUserMenu = true"
              @mouseleave="showUserMenu = false"
              >{{ url.name }}</NavBarItem
            >
            <NavBarItem v-else :url="url.url">{{ url.name }}</NavBarItem>
            <div
              v-if="url.id === 'user'"
              v-show="showUserMenu"
              class="absolute flex flex-col bg-light-gray p-4 text-white ease-in-out"
              @mouseover="showUserMenu = true"
              @mouseleave="showUserMenu = false"
            >
              <router-link to="/user/profil">Profil</router-link>
              <router-link to="/user/samochody">Samochody</router-link>
              <router-link to="/user/rezerwacje">Rezerwacje</router-link>
              <router-link
                v-if="userRole === 'ADMIN' || userRole === 'EMPLOYEE'"
                to="/user/kalendarz"
                >Kalendarz</router-link
              >
              <router-link to="" @click="logout()">Wyloguj</router-link>
            </div>
          </li>
        </ul>
      </div>
      <div
        id="navbar-mobile"
        class="hidden min-h-screen w-full translate-x-full bg-light-gray bg-opacity-30 backdrop-blur-sm transition-transform duration-300 md:hidden"
      >
        <ul v-if="!loggedIn" class="flex flex-col pl-10 pt-10 font-medium">
          <li v-for="url in urls" :key="url.id">
            <NavBarItem v-if="url.id === 'login'" :url="url.url">{{ url.name }}</NavBarItem>
          </li>
        </ul>
        <ul v-else class="flex flex-col pl-10 pt-10 font-medium text-white">
          <router-link to="/user/profil">Profil</router-link>
          <router-link to="/user/samochody">Samochody</router-link>
          <router-link to="/user/rezerwacje">Rezerwacje</router-link>
          <router-link to="" @click="logout()">Wyloguj</router-link>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import NavBarItem from '@/components/NavBarItem.vue'
import { useAuthStore } from '@/stores/auth'
import { isLoggedIn } from '@/authStatus'

export default {
  name: 'NavBar',
  components: {
    NavBarItem
  },
  setup() {
    return { loggedIn: isLoggedIn }
  },
  data() {
    return {
      urls: [
        { id: 'about', name: 'O nas', url: '/' },
        { id: 'oferta', name: 'Oferta', url: '/oferta' },
        { id: 'reserve', name: 'Rezerwuj', url: '/rezerwuj' },
        { id: 'login', name: 'Zaloguj', url: '/login' },
        { id: 'register', name: 'Zarejestruj', url: '/rejestracja' }
      ],
      loggedUrls: [
        { id: 'about', name: 'O nas', url: '/' },
        { id: 'oferta', name: 'Oferta', url: '/oferta' },
        { id: 'reserve', name: 'Rezerwuj', url: '/rezerwuj' },
        {
          id: 'user',
          name: 'USER',
          url: '/',
          children: [
            { id: 'profile', name: 'Profil', url: '/' },
            { id: 'orders', name: 'Rezerwacje', url: '/user/rezerwacje' },
            { id: 'cars', name: 'Samochody', url: '/user/cars' },
            { id: 'calendar', name: 'Kalendarz', url: '/user/kalendarz' }
            // { id: 'logout', name: 'Wyloguj', url: '/' },
          ]
        }
      ],
      showUserMenu: false,
      userRole: JSON.parse(localStorage.getItem('role')) || ""
    }
  },
  created() {
    // this.userRole = JSON.parse(localStorage.getItem('role'))
    console.log("userRole:", this.userRole);
  },
  methods: {
    toggleMenu() {
      const navbar = document.querySelector('#navbar-mobile')
      navbar.classList.toggle('translate-x-1/4')
      navbar.classList.toggle('translate-x-full')
      navbar.classList.toggle('hidden')
    },
    navScroll() {
      const navbar = document.querySelector('#navbar')

      navbar.classList.toggle('bg-mcl-orange')
    },
    logout() {
      useAuthStore().logout()
      isLoggedIn.value = false
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    }
  }
}
</script>

<style>
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}
</style>
