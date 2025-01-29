<template>
  <div class="min-h-screen bg-dark-mode-gray">
    <div class="mx-auto flex max-w-screen-xl flex-col flex-wrap gap-6 p-4">
      <h1 class="border-b-2 pb-2 text-4xl font-medium text-white">REZERWACJE</h1>
      <div
        v-for="order in orderList"
        :key="order.res_id"
        class="flex max-w-screen-md rounded-lg border-2 border-transparent text-white hover:border-white"
      >
        <div class="bg w-1/4 rounded-l-lg bg-light-gray py-6 pl-6">
          <div class="flex flex-col">
            <span> {{ extractDateAndTime(order.res_start_at)['date'] }}</span>
            <span>
              {{ extractDateAndTime(order.res_start_at)['time'] }} -
              {{ extractDateAndTime(order.res_end_at)['time'] }}</span
            >
            <span class="text-xkom-gray"> nr {{ order.res_id }}</span>
            <span class="mt-2">{{ order.services_price }} zł</span>
          </div>
        </div>
        <div class="flex w-3/4 justify-between rounded-r-lg bg-dark-gray">
          <div class="my-auto flex flex-col px-10">
            <span class="justify-end text-xl font-medium"> {{ order.services_name }}</span>
            <span class="text-lg">
              {{ order.cars_year }} {{ order.cars_make }} {{ order.cars_model }}</span
            >
            <span> {{ order.cars_colour }}</span>
          </div>
          <div class="">
            <button
              class="m-4 rounded-lg border border-transparent px-4 py-2 font-medium hover:border hover:border-mcl-orange"
              @click="deleteReservation(order.res_id)"
            >
              Anuluj
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'OrdersPage',
  data() {
    return {
      items: [
        { id: 1, label: 'Option 1', value: 'option1' },
        { id: 2, label: 'Option 2', value: 'option2' },
        { id: 3, label: 'Option 3', value: 'option3' }
      ],
      orderList: [],
      selectedItems: [],
      userId: ''
    }
  },
  async mounted() {
    await this.fetchUserId()
    await this.fetchOrders()
  },
  methods: {
    async fetchOrders() {
      const response = await axios.get('api/users/' + this.userId + '/reservations')
      const reservationList = response.data['content']

      reservationList.forEach((reservation) => {
        this.orderList.push(reservation)
      })
    },
    async fetchUserId() {
      const response = await axios.get('api/users/user')
      this.userId = response.data['id']
    },
    async fetchUserCars() {
      const response = await axios.get('api/users/' + this.userId + '/cars')
      console.log(response.data['content'])
    },
    extractDateAndTime(timestamp) {
      const dateObj = new Date(timestamp)

      const year = dateObj.getFullYear()
      const month = ('0' + (dateObj.getMonth() + 1)).slice(-2) // Months are 0-indexed
      const day = ('0' + dateObj.getDate()).slice(-2)
      const date = `${year}-${month}-${day}`

      const hours = ('0' + dateObj.getHours()).slice(-2)
      const minutes = ('0' + dateObj.getMinutes()).slice(-2)
      const time = `${hours}:${minutes}`

      return { date, time }
    },
    async deleteReservation(res_id) {
      const response = await axios.delete(
        '/api/reservations/' + this.userId + '/delete-reservation/' + res_id
      )
      if (response.status == 200) {
        this.orderList.length = 0
        await this.fetchOrders()
      }
    }
  }
}
</script>
