<template>
  <div class="flex flex-col justify-center text-white">
    <div class="mx-auto mt-10 flex pt-6 text-2xl">
      <span>Zarezerwuj usługę</span>
    </div>

    <div class="mx-auto mt-4 flex flex-col">
      <form method="post" class="flex flex-col gap-5" @submit.prevent="addReservation">
        <div class="flex flex-col gap-2">
          <label for="service-selector">Wybierz usługę</label>
          <select
            id="service-selector"
            v-model="selectedService"
            name="service-selector"
            class="h-10 rounded bg-light-gray pl-2"
          >
            <option v-for="service in services" :key="service" :value="service.name">
              {{ service.name }}
            </option>
          </select>
        </div>
        <div class="flex flex-col gap-2">
          <label for="car-selector">Wybierz samochód</label>
          <select
            id="car-selector"
            v-model="selectedCar"
            name="car-selector"
            class="h-10 rounded bg-light-gray pl-2"
          >
            <option v-for="car in carList" :key="car" :value="car">
              {{ car.production_year }} {{ car.make }} {{ car.model }}
            </option>
          </select>
        </div>
        <div class="flex flex-col gap-2">
          <label for="date-picker">Wybierz datę</label>
          <VueDatePicker
            id="date"
            v-model="selectedDate"
            name="date-picker"
            dark
            :enable-time-picker="false"
          />
        </div>
        <div class="flex flex-col gap-2">
          <label for="time-selector">Wybierz godzinę</label>
          <select
            id="time-selector"
            v-model="selectedTime"
            name="time-selector"
            class="h-10 rounded bg-light-gray pl-2"
          >
            <option v-for="slot in availableSlots" :key="slot" :value="slot">
              {{ slot }}
            </option>
          </select>
        </div>
        <div class="mx-auto">
          <button type="submit" class="rounded-md bg-mcl-orange p-2 px-10">Zarezerwuj</button>
        </div>
      </form>
      <div v-if="reservationSuccessful" class="mx-auto mt-4">
        <span>Rezerwacja została złożona</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReserveForm',
  data() {
    return {
      services: [],
      carSizes: [
        { value: 'small', name: 'Małe' },
        { value: 'medium', name: 'Średnie' },
        { value: 'large', name: 'Duże' }
      ],
      userId: '',
      carList: [],
      selectedCar: '',
      selectedService: '',
      selectedDate: '',
      timeList: [],
      selectedTime: '',
      availableSlots: [],
      reservationSuccessful: false
    }
  },
  watch: {
    selectedService: 'updateAvailableSlots',
    selectedCar: 'updateAvailableSlots',
    selectedDate: 'updateAvailableSlots'
  },
  async mounted() {
    await this.getServices()
    await this.fetchUserId()
    await this.fetchUserCars()
  },
  methods: {
    async fetchUserId() {
      const response = await axios.get('api/users/user')
      this.userId = response.data['id']
    },
    async fetchUserCars() {
      const response = await axios.get('api/users/' + this.userId + '/cars')
      const carList = response.data['content']

      carList.forEach((car) => {
        this.carList.push(car)
      })
    },
    async getServices() {
      const response = await axios.get('api/services')
      this.services = response.data['content']
    },
    async getServiceInfo() {
      const response = await axios.get(
        'api/services/' + this.selectedService + '/' + this.selectedCar.size
      )
      return response.data[0]
    },
    async getAvailableTimes() {
      const serviceInfo = await this.getServiceInfo()
      const length = this.extractTimeFromString(serviceInfo.length)
      const response = await axios.get('api/reservations/daily-hours', {
        params: {
          length_hours: parseInt(length.hours),
          length_minutes: parseInt(length.minutes)
        }
      })

      this.availableSlots = response.data[this.getDateFromPicker()]
      if (this.availableSlots.length == 0) {
        this.availableSlots.push('Brak wolnych terminów')
      }
    },
    getDateFromPicker() {
      if (this.selectedDate != '') {
        const day = String(this.selectedDate.getDate()).padStart(2, '0')
        const month = String(this.selectedDate.getMonth() + 1).padStart(2, '0')
        const year = this.selectedDate.getFullYear()

        return `${year}-${month}-${day}`
      } else {
        return ''
      }
    },
    extractTimeFromString(time) {
      const timeArray = time.split(':')
      return {
        hours: timeArray[0],
        minutes: timeArray[1],
        seconds: timeArray[2]
      }
    },
    updateAvailableSlots() {
      if (this.selectedService && this.selectedCar && this.selectedDate) {
        this.getAvailableTimes()
      }
    },
    async addReservation() {
      const serviceInfo = await this.getServiceInfo()
      const length = this.extractTimeFromString(serviceInfo.length)
      const response = await axios.post('api/reservations/' + this.userId + '/add-reservation', {
        service_id: serviceInfo.id,
        car_id: parseInt(this.selectedCar.id),
        start_at_date: this.getDateFromPicker(),
        start_at_time: this.selectedTime,
        length: {
          hours: parseInt(length.hours),
          minutes: parseInt(length.minutes)
        }
      })

      if (response.status == 200) {
        this.reservationSuccessful = true
        await this.getAvailableTimes()
      }
    }
  }
}
</script>

<script setup>
import axios from 'axios'
</script>
